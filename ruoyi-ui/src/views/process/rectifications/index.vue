<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="整改状态" prop="rectificationStatus">
        <el-select v-model="queryParams.rectificationStatus" placeholder="请选择整改状态" clearable>
          <el-option
            v-for="dict in dict.type.rectification_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="验收结果" prop="acceptanceResult">
        <el-select v-model="queryParams.acceptanceResult" placeholder="请选择验收结果" clearable>
          <el-option
            v-for="dict in dict.type.acceptance_result"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['process:rectifications:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['process:rectifications:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['process:rectifications:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['process:rectifications:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="rectificationsList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" v-if="columns[0].visible" />
      <el-table-column label="序号" align="center" prop="id" v-if="columns[1].visible" />
      <el-table-column label="隐患编号" align="center" prop="rectificationCode" v-if="columns[2].visible" />
      <el-table-column label="关联检查记录" align="center" prop="inspectionId" v-if="columns[3].visible">
        <template slot-scope="scope">
          <span v-if="scope.row.inspection && scope.row.inspection.inspectionCode">
            {{ scope.row.inspection.inspectionCode }}
          </span>
          <span v-else-if="selectedInspectionMap[scope.row.inspectionId]">
            {{ selectedInspectionMap[scope.row.inspectionId] }}
          </span>
          <span v-else>{{ scope.row.inspectionId }}</span>
        </template>
      </el-table-column>
      <el-table-column label="隐患描述" align="center" prop="hazardDescription" v-if="columns[4].visible" />
      <el-table-column label="整改要求" align="center" prop="rectificationRequirements" v-if="columns[5].visible" />
      <el-table-column label="整改期限" align="center" prop="rectificationDeadline" width="180" v-if="columns[6].visible">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.rectificationDeadline, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="隐患照片" align="center" prop="hazardPhotos" width="100" v-if="columns[7].visible">
        <template slot-scope="scope">
          <image-preview :src="scope.row.hazardPhotos" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="处罚依据" align="center" prop="penaltyBasis" v-if="columns[8].visible" />
      <el-table-column label="处罚金额(元)" align="center" prop="penaltyAmount" v-if="columns[9].visible" />
      <el-table-column label="整改状态" align="center" prop="rectificationStatus" v-if="columns[10].visible">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.rectification_status" :value="scope.row.rectificationStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="验收人" align="center" prop="acceptancePerson" v-if="columns[11].visible" />
      <el-table-column label="验收日期" align="center" prop="acceptanceDate" width="180" v-if="columns[12].visible">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.acceptanceDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="验收结果" align="center" prop="acceptanceResult" v-if="columns[13].visible">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.acceptance_result" :value="scope.row.acceptanceResult"/>
        </template>
      </el-table-column>
      <el-table-column label="整改照片" align="center" prop="acceptancePhotos" width="100" v-if="columns[14].visible">
        <template slot-scope="scope">
          <image-preview :src="scope.row.acceptancePhotos" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="notes" v-if="columns[15].visible" />
      <el-table-column label="创建时间" align="center" prop="createdAt" width="180" v-if="columns[16].visible">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createdAt, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" v-if="columns[17].visible">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['process:rectifications:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['process:rectifications:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改隐患整改记录对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="关联检查记录" prop="inspectionId">
          <el-input v-model="form.inspectionId" placeholder="请选择关联检查记录" readonly>
            <el-button slot="append" icon="el-icon-search" @click="handleSelectInspection"></el-button>
          </el-input>
          <div v-if="selectedInspection" style="margin-top: 5px;">
            <el-tag type="info">{{ selectedInspection.inspectionCode }}</el-tag>
          </div>
        </el-form-item>
        <!-- 隐患编号字段仅在修改时显示 -->
        <el-form-item label="隐患编号" prop="rectificationCode" v-if="form.id != null">
          <el-input v-model="form.rectificationCode" placeholder="请输入隐患编号" readonly />
        </el-form-item>
        <el-form-item label="隐患描述" prop="hazardDescription">
          <el-input v-model="form.hazardDescription" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="整改要求" prop="rectificationRequirements">
          <el-input v-model="form.rectificationRequirements" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="整改期限" prop="rectificationDeadline">
          <el-date-picker clearable
                          v-model="form.rectificationDeadline"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择整改期限">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="隐患照片" prop="hazardPhotos">
          <image-upload v-model="form.hazardPhotos"/>
        </el-form-item>
        <el-form-item label="处罚依据" prop="penaltyBasis">
          <el-input v-model="form.penaltyBasis" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="处罚金额(元)" prop="penaltyAmount">
          <el-input v-model="form.penaltyAmount" placeholder="请输入处罚金额" />
        </el-form-item>
        <el-form-item label="整改状态" prop="rectificationStatus">
          <el-select v-model="form.rectificationStatus" placeholder="请选择整改状态">
            <el-option
              v-for="dict in dict.type.rectification_status"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="验收人" prop="acceptancePerson">
          <el-input v-model="form.acceptancePerson" placeholder="请选择验收人" readonly :disabled="!['3', '4'].includes(form.rectificationStatus)">
            <el-button slot="append" icon="el-icon-search" @click="handleSelectUser" :disabled="form.rectificationStatus !== '3' && form.rectificationStatus !== '4'"></el-button>
          </el-input>
        </el-form-item>
        <el-form-item label="验收日期" prop="acceptanceDate">
          <el-date-picker clearable
                          v-model="form.acceptanceDate"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择验收日期"
                          :disabled="form.rectificationStatus !== '3' && form.rectificationStatus !== '4'">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="验收结果" prop="acceptanceResult">
          <el-select v-model="form.acceptanceResult" placeholder="请选择验收结果" :disabled="form.rectificationStatus !== '3' && form.rectificationStatus !== '4'">
            <el-option
              v-for="dict in dict.type.acceptance_result"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="整改照片" prop="acceptancePhotos">
          <image-upload v-model="form.acceptancePhotos" :disabled="form.rectificationStatus !== '3' && form.rectificationStatus !== '4'" />
        </el-form-item>
        <el-form-item label="备注" prop="notes">
          <el-input v-model="form.notes" type="textarea" placeholder="请输入内容" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 监督检查记录选择对话框 -->
    <el-dialog title="选择监督检查记录" :visible.sync="inspectionSelectOpen" width="1100px" append-to-body>
      <el-form :model="inspectionQueryParams" ref="inspectionQueryForm" size="small" :inline="true" label-width="68px">
        <el-form-item label="项目名称">
          <el-input
            v-model="inspectionQueryParams.projectName"
            placeholder="请输入项目名称"
            clearable
            @keyup.enter.native="handleInspectionQuery"
          />
        </el-form-item>
        <el-form-item label="承包商">
          <el-input
            v-model="inspectionQueryParams.contractorName"
            placeholder="请输入承包商"
            clearable
            @keyup.enter.native="handleInspectionQuery"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleInspectionQuery">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetInspectionQuery">重置</el-button>
        </el-form-item>
      </el-form>
      <el-table v-loading="inspectionLoading" :data="inspectionList" @row-dblclick="handleSelectInspectionRow" highlight-current-row>
        <el-table-column label="检查记录编号" align="center" prop="inspectionCode" />
        <el-table-column label="项目名称" align="center" prop="projectName" />
        <el-table-column label="承包商" align="center" prop="contractorName" />
        <el-table-column label="检查时间" align="center" prop="inspectionDatetime" width="180">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.inspectionDatetime, '{y}-{m}-{d}') }}</span>
          </template>
        </el-table-column>
        <el-table-column label="检查人员" align="center" prop="inspectorName" />
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-check"
              @click="handleSelectInspectionRow(scope.row)"
            >选择</el-button>
          </template>
        </el-table-column>
      </el-table>

      <pagination
        v-show="inspectionTotal>0"
        :total="inspectionTotal"
        :page.sync="inspectionQueryParams.pageNum"
        :limit.sync="inspectionQueryParams.pageSize"
        @pagination="getInspectionList"
      />

      <div slot="footer" class="dialog-footer">
        <el-button @click="inspectionSelectOpen = false">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 验收人选择对话框 -->
    <el-dialog :title="userDialogTitle" :visible.sync="userSelectOpen" width="1100px" append-to-body>
      <el-form :model="userQueryParams" ref="userQueryForm" size="small" :inline="true" label-width="68px">
        <el-form-item label="用户名称">
          <el-input
            v-model="userQueryParams.userName"
            placeholder="请输入用户名称"
            clearable
            @keyup.enter.native="handleUserQuery"
          />
        </el-form-item>
        <el-form-item label="手机号码">
          <el-input
            v-model="userQueryParams.phonenumber"
            placeholder="请输入手机号码"
            clearable
            @keyup.enter.native="handleUserQuery"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleUserQuery">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetUserQuery">重置</el-button>
        </el-form-item>
      </el-form>

      <el-table v-loading="userLoading" :data="userList" @row-dblclick="handleSelectUserRow">
        <el-table-column label="用户编号" align="center" prop="userId" />
        <el-table-column label="用户名称" align="center" prop="userName" />
        <el-table-column label="昵称" align="center" prop="nickName" />
        <el-table-column label="部门" align="center" prop="dept.deptName" />
        <el-table-column label="手机号码" align="center" prop="phonenumber" />
        <el-table-column label="状态" align="center" prop="status">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.sys_normal_disable" :value="scope.row.status"/>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-check"
              @click="handleSelectUserRow(scope.row)"
            >选择</el-button>
          </template>
        </el-table-column>
      </el-table>

      <pagination
        v-show="userTotal>0"
        :total="userTotal"
        :page.sync="userQueryParams.pageNum"
        :limit.sync="userQueryParams.pageSize"
        @pagination="getUserList"
      />

      <div slot="footer" class="dialog-footer">
        <el-button @click="userSelectOpen = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listRectifications, getRectifications, delRectifications, addRectifications, updateRectifications } from "@/api/process/rectifications";
import { listInspections } from "@/api/process/inspections";
import { listUser } from "@/api/system/user";

export default {
  name: "Rectifications",
  dicts: ['rectification_status', 'acceptance_result', 'sys_normal_disable'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 隐患整改记录表格数据
      rectificationsList: [],
      // 选中的检查记录映射
      selectedInspectionMap: {},
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 是否显示监督检查记录选择弹出层
      inspectionSelectOpen: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        hazardDescription: null,
        rectificationStatus: null,
        acceptanceResult: null,
      },
      // 监督检查记录查询参数
      inspectionQueryParams: {
        pageNum: 1,
        pageSize: 10,
        projectName: null,
        contractorName: null,
        hasHazards: "2"  // 只查询存在隐患的记录（根据字典，存在隐患的键值是2）
      },
      // 监督检查记录总条数
      inspectionTotal: 0,
      // 监督检查记录表格数据
      inspectionList: [],
      // 监督检查记录加载状态
      inspectionLoading: false,
      // 已选择的监督检查记录
      selectedInspection: null,
      // 是否显示用户选择弹出层
      userSelectOpen: false,
      // 用户选择对话框标题
      userDialogTitle: "选择验收人",
      // 用户查询参数
      userQueryParams: {
        pageNum: 1,
        pageSize: 10,
        userName: null,
        phonenumber: null
      },
      // 用户总条数
      userTotal: 0,
      // 用户表格数据
      userList: [],
      // 用户加载状态
      userLoading: false,
      // 表单参数
      form: {},
      // 表格列显隐
      columns: [
        { key: 0, visible: true, label: "选择列" },
        { key: 1, visible: true, label: "序号" },
        { key: 2, visible: true, label: "隐患编号" },
        { key: 3, visible: true, label: "关联检查记录" },
        { key: 4, visible: true, label: "隐患描述" },
        { key: 5, visible: true, label: "整改要求" },
        { key: 6, visible: true, label: "整改期限" },
        { key: 7, visible: true, label: "隐患照片" },
        { key: 8, visible: true, label: "处罚依据" },
        { key: 9, visible: true, label: "处罚金额(元)" },
        { key: 10, visible: true, label: "整改状态" },
        { key: 11, visible: true, label: "验收人" },
        { key: 12, visible: true, label: "验收日期" },
        { key: 13, visible: true, label: "验收结果" },
        { key: 14, visible: true, label: "整改照片" },
        { key: 15, visible: true, label: "备注" },
        { key: 16, visible: true, label: "创建时间" },
        { key: 17, visible: true, label: "操作" }
      ],
      // 表单校验
      rules: {
        id: [
          { required: true, message: "序号不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询隐患整改记录列表 */
    getList() {
      this.loading = true;
      listRectifications(this.queryParams).then(response => {
        this.rectificationsList = response.rows;
        this.total = response.total;
        // 构建检查记录编号映射
        this.buildInspectionCodeMap();
        this.loading = false;
      });
    },
    // 构建检查记录编号映射
    buildInspectionCodeMap() {
      this.rectificationsList.forEach(item => {
        if (item.inspection && item.inspection.inspectionCode) {
          this.selectedInspectionMap[item.inspectionId] = item.inspection.inspectionCode;
        }
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加隐患整改记录";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getRectifications(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改隐患整改记录";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateRectifications(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addRectifications(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除隐患整改记录编号为"' + ids + '"的数据项？').then(function() {
        return delRectifications(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('process/rectifications/export', {
        ...this.queryParams
      }, `rectifications_${new Date().getTime()}.xlsx`)
    },
    /** 选择监督检查记录 */
    handleSelectInspection() {
      this.inspectionSelectOpen = true;
      this.getInspectionList();
    },
    /** 查询监督检查记录列表 */
    getInspectionList() {
      this.inspectionLoading = true;
      // 确保只查询存在隐患的记录（has_hazards字典中存在隐患的键值是2）
      const params = {...this.inspectionQueryParams, hasHazards: "2"};
      listInspections(params).then(response => {
        this.inspectionList = response.rows;
        this.inspectionTotal = response.total;
        this.inspectionLoading = false;
      });
    },
    /** 搜索监督检查记录 */
    handleInspectionQuery() {
      this.inspectionQueryParams.pageNum = 1;
      this.inspectionQueryParams.hasHazards = "2";  // 确保搜索时也只查询存在隐患的记录（键值是2）
      this.getInspectionList();
    },
    /** 重置监督检查记录查询 */
    resetInspectionQuery() {
      this.inspectionQueryParams = {
        pageNum: 1,
        pageSize: 10,
        projectName: null,
        contractorName: null,
        hasHazards: "2"  // 保持只查询存在隐患的记录（键值是2）
      };
      this.getInspectionList();
    },
    /** 选择监督检查记录行 */
    handleSelectInspectionRow(row) {
      this.selectedInspection = row;
      this.form.inspectionId = row.id;
      // 更新映射表
      this.selectedInspectionMap[row.id] = row.inspectionCode;
      this.inspectionSelectOpen = false;
    },

    /** 选择验收人 */
    handleSelectUser() {
      this.userSelectOpen = true;
      this.getUserList();
    },

    /** 查询用户列表 */
    getUserList() {
      this.userLoading = true;
      listUser(this.userQueryParams).then(response => {
        this.userList = response.rows;
        this.userTotal = response.total;
        this.userLoading = false;
      });
    },

    /** 选择用户行 */
    handleSelectUserRow(row) {
      this.form.acceptancePerson = row.userName;
      this.userSelectOpen = false;
    },

    /** 搜索用户 */
    handleUserQuery() {
      this.userQueryParams.pageNum = 1;
      this.getUserList();
    },

    /** 重置用户查询 */
    resetUserQuery() {
      this.userQueryParams = {
        pageNum: 1,
        pageSize: 10,
        userName: null,
        phonenumber: null
      };
      this.getUserList();
    },

    /** 表单重置 */
    reset() {
      this.form = {
        id: null,
        rectificationCode: null,
        inspectionId: null,
        hazardDescription: null,
        rectificationRequirements: null,
        rectificationDeadline: null,
        hazardPhotos: null,
        penaltyBasis: null,
        penaltyAmount: null,
        rectificationStatus: null,
        acceptancePerson: null,
        acceptanceDate: null,
        acceptanceResult: null,
        acceptancePhotos: null,
        notes: null,
        createdBy: null,
        createdAt: null,
        updatedBy: null,
        updatedAt: null
      };
      this.selectedInspection = null;
      this.resetForm("form");
    },
  }
};
</script>
