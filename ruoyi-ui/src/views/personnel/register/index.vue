<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="承包商" prop="contractorName">
        <el-input
          v-model="queryParams.contractorName"
          placeholder="请输入承包商"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="姓名" prop="personnelName">
        <el-input
          v-model="queryParams.personnelName"
          placeholder="请输入姓名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="职务" prop="title">
        <el-select v-model="queryParams.title" placeholder="请选择职务" clearable>
          <el-option
            v-for="dict in dict.type.contractor_title"
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
          v-hasPermi="['personnel:register:add']"
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
          v-hasPermi="['personnel:register:edit']"
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
          v-hasPermi="['personnel:register:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['personnel:register:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="registerList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" prop="personnelId" v-if="columns[0].visible" />
      <el-table-column label="项目名称" align="center" prop="projectName" v-if="columns[1].visible" />
      <el-table-column label="承包商" align="center" prop="contractorName" v-if="columns[2].visible" />
      <el-table-column label="身份证号" align="center" prop="idCardNumber" v-if="columns[3].visible" />
      <el-table-column label="姓名" align="center" prop="personnelName" v-if="columns[4].visible" />
      <el-table-column label="职务" align="center" prop="title" v-if="columns[5].visible">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.contractor_title" :value="scope.row.title"/>
        </template>
      </el-table-column>
      <el-table-column label="性别" align="center" prop="gender" v-if="columns[6].visible">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_user_sex" :value="scope.row.gender === '男' ? '0' : scope.row.gender === '女' ? '1' : scope.row.gender"/>
        </template>
      </el-table-column>
      <el-table-column label="年龄" align="center" prop="age" v-if="columns[7].visible" />
      <el-table-column label="工种" align="center" prop="workType" v-if="columns[8].visible" />
      <el-table-column label="联系方式" align="center" prop="contactPhone" v-if="columns[9].visible" />
      <el-table-column label="家庭住址" align="center" prop="homeAddress" v-if="columns[10].visible" />
      <el-table-column label="体检日期" align="center" prop="physicalExamDate" width="180" v-if="columns[11].visible">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.physicalExamDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="体检结果" align="center" prop="physicalExamResult" v-if="columns[12].visible">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.contractor_exam_result" :value="scope.row.physicalExamResult"/>
        </template>
      </el-table-column>
      <el-table-column label="体检记录" align="center" prop="physicalExamRecord" v-if="columns[13].visible">
        <template slot-scope="scope">
          <div v-if="scope.row.physicalExamRecord && scope.row.physicalExamRecord.length > 0">
            <div v-for="(file, index) in scope.row.physicalExamRecord.split(',')" :key="index" class="file-item">
              <el-link type="primary" @click="downloadFile(file)">
                <i class="el-icon-document"></i>
                {{ getFileName(file) }}
              </el-link>
            </div>
          </div>
          <span v-else>无</span>
        </template>
      </el-table-column>
      <el-table-column label="入场时间" align="center" prop="entryFactoryTime" width="180" v-if="columns[14].visible">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.entryFactoryTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="离场时间" align="center" prop="exitFactoryTime" width="180" v-if="columns[15].visible">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.exitFactoryTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remarks" v-if="columns[16].visible" />
      <el-table-column label="创建时间" align="center" prop="createdAt" width="180" v-if="columns[17].visible">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createdAt, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" v-if="columns[18].visible">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['personnel:register:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['personnel:register:remove']"
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

    <!-- 添加或修改人员信息台账对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="项目名称" prop="projectName">
          <el-input v-model="form.projectName" placeholder="请输入项目名称">
            <el-button slot="append" icon="el-icon-search" @click="handleSelectProject"></el-button>
          </el-input>
        </el-form-item>
        <el-form-item label="承包商" prop="contractorName">
          <el-input v-model="form.contractorName" placeholder="请输入承包商" />
        </el-form-item>
        <el-form-item label="身份证号" prop="idCardNumber">
          <el-input v-model="form.idCardNumber" placeholder="请输入身份证号" />
        </el-form-item>
        <el-form-item label="姓名" prop="personnelName">
          <el-input v-model="form.personnelName" placeholder="请输入姓名" />
        </el-form-item>
        <el-form-item label="职务" prop="title">
          <el-select v-model="form.title" placeholder="请选择职务">
            <el-option
              v-for="dict in dict.type.contractor_title"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="性别" prop="gender">
          <el-select v-model="form.gender" placeholder="请选择性别">
            <el-option
              v-for="dict in dict.type.sys_user_sex"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="年龄" prop="age">
          <el-input v-model="form.age" placeholder="请输入年龄" />
        </el-form-item>
        <el-form-item label="工种" prop="workType">
          <el-input v-model="form.workType" placeholder="请输入工种" />
        </el-form-item>
        <el-form-item label="联系方式" prop="contactPhone">
          <el-input v-model="form.contactPhone" placeholder="请输入联系方式" />
        </el-form-item>
        <el-form-item label="家庭住址" prop="homeAddress">
          <el-input v-model="form.homeAddress" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="体检日期" prop="physicalExamDate">
          <el-date-picker clearable
            v-model="form.physicalExamDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择体检日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="体检结果" prop="physicalExamResult">
          <el-select v-model="form.physicalExamResult" placeholder="请选择体检结果">
            <el-option
              v-for="dict in dict.type.contractor_exam_result"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="体检记录" prop="physicalExamRecord">
          <file-upload v-model="form.physicalExamRecord"/>
        </el-form-item>
        <el-form-item label="入场时间" prop="entryFactoryTime">
          <el-date-picker clearable
            v-model="form.entryFactoryTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择入场时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="离场时间" prop="exitFactoryTime">
          <el-date-picker clearable
            v-model="form.exitFactoryTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择离场时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="备注" prop="remarks">
          <el-input v-model="form.remarks" type="textarea" placeholder="请输入内容" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 项目选择对话框 -->
    <el-dialog :title="projectDialogTitle" :visible.sync="projectSelectOpen" width="1100px" append-to-body>
      <el-form :model="projectQueryParams" ref="projectQueryForm" size="small" :inline="true" label-width="68px">
        <el-form-item label="项目名称">
          <el-input
            v-model="projectQueryParams.projectName"
            placeholder="请输入项目名称"
            clearable
            @keyup.enter.native="handleProjectQuery"
          />
        </el-form-item>
        <el-form-item label="拟选承包商">
          <el-input
            v-model="projectQueryParams.selectedContractor"
            placeholder="请输入拟选承包商"
            clearable
            @keyup.enter.native="handleProjectQuery"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleProjectQuery">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetProjectQuery">重置</el-button>
        </el-form-item>
      </el-form>
      <el-table v-loading="projectLoading" :data="projectList" @row-dblclick="handleSelectProjectRow">
        <el-table-column label="项目名称" align="center" prop="projectName" />
        <el-table-column label="拟选承包商" align="center" prop="selectedContractor" />
        <el-table-column label="流程状态" align="center" prop="processStatus">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.process_status" :value="scope.row.processStatus"/>
          </template>
        </el-table-column>
        <el-table-column label="当前节点" align="center" prop="currentNode" />
        <el-table-column label="当前处理人" align="center" prop="currentHandler" />
        <el-table-column label="创建时间" align="center" prop="createdAt" width="180">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.createdAt, '{y}-{m}-{d}') }}</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-check"
              @click="handleSelectProjectRow(scope.row)"
            >选择</el-button>
          </template>
        </el-table-column>
      </el-table>

      <pagination
        v-show="projectTotal>0"
        :total="projectTotal"
        :page.sync="projectQueryParams.pageNum"
        :limit.sync="projectQueryParams.pageSize"
        @pagination="getProjectList"
      />

      <div slot="footer" class="dialog-footer">
        <el-button @click="projectSelectOpen = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listRegister, getRegister, delRegister, addRegister, updateRegister } from "@/api/personnel/register";
import { listChoice } from "@/api/choice/choice";

export default {
  name: "Register",
  dicts: ['contractor_title', 'sys_user_sex', 'contractor_exam_result', 'process_status'],
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
      // 人员信息台账表格数据
      registerList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 项目选择对话框标题
      projectDialogTitle: "选择项目",
      // 是否显示项目选择弹出层
      projectSelectOpen: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        contractorName: null,
        personnelName: null,
        title: null,
      },
      // 项目查询参数
      projectQueryParams: {
        pageNum: 1,
        pageSize: 10,
        projectName: null,
        selectedContractor: null
      },
      // 项目总条数
      projectTotal: 0,
      // 项目表格数据
      projectList: [],
      // 项目加载状态
      projectLoading: false,
      // 表单参数
      form: {},
      // 列信息
      columns: [
        { key: 0, label: `序号`, visible: true },
        { key: 1, label: `项目名称`, visible: true },
        { key: 2, label: `承包商`, visible: true },
        { key: 3, label: `身份证号`, visible: true },
        { key: 4, label: `姓名`, visible: true },
        { key: 5, label: `职务`, visible: true },
        { key: 6, label: `性别`, visible: true },
        { key: 7, label: `年龄`, visible: true },
        { key: 8, label: `工种`, visible: true },
        { key: 9, label: `联系方式`, visible: true },
        { key: 10, label: `家庭住址`, visible: true },
        { key: 11, label: `体检日期`, visible: true },
        { key: 12, label: `体检结果`, visible: true },
        { key: 13, label: `体检记录`, visible: true },
        { key: 14, label: `入场时间`, visible: true },
        { key: 15, label: `离场时间`, visible: true },
        { key: 16, label: `备注`, visible: true },
        { key: 17, label: `创建时间`, visible: true },
        { key: 18, label: `操作`, visible: true }
      ],
      // 表单校验
      rules: {
        projectName: [
          { required: true, message: "项目名称不能为空", trigger: "blur" }
        ],
        contractorName: [
          { required: true, message: "承包商不能为空", trigger: "blur" }
        ],
        idCardNumber: [
          { required: true, message: "身份证号不能为空", trigger: "blur" }
        ],
        personnelName: [
          { required: true, message: "姓名不能为空", trigger: "blur" }
        ],
        title: [
          { required: true, message: "职务不能为空", trigger: "change" }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询人员信息台账列表 */
    getList() {
      this.loading = true;
      listRegister(this.queryParams).then(response => {
        this.registerList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        personnelId: null,
        choiceId: null,
        projectName: null,
        contractorName: null,
        idCardNumber: null,
        personnelName: null,
        title: null,
        gender: null,
        age: null,
        workType: null,
        contactPhone: null,
        homeAddress: null,
        physicalExamDate: null,
        physicalExamResult: null,
        physicalExamRecord: null,
        entryFactoryTime: null,
        exitFactoryTime: null,
        remarks: null,
        createdBy: null,
        createdAt: null,
        updatedBy: null,
        updatedAt: null
      };
      this.resetForm("form");
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
      this.ids = selection.map(item => item.personnelId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加人员信息台账";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const personnelId = row.personnelId || this.ids
      getRegister(personnelId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改人员信息台账";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.personnelId != null) {
            updateRegister(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addRegister(this.form).then(response => {
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
      const personnelIds = row.personnelId || this.ids;
      this.$modal.confirm('是否确认删除人员信息台账编号为"' + personnelIds + '"的数据项？').then(function() {
        return delRegister(personnelIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('personnel/register/export', {
        ...this.queryParams
      }, `register_${new Date().getTime()}.xlsx`)
    },
    /** 获取文件名 */
    getFileName(filePath) {
      if (!filePath) return '';
      // 提取文件名
      const fileName = filePath.split('/').pop();
      return fileName || filePath;
    },
    /** 下载文件 */
    downloadFile(filePath) {
      // 使用resource方法下载文件
      this.$download.resource(filePath);
    },
    /** 选择项目按钮操作 */
    handleSelectProject() {
      this.projectSelectOpen = true;
      this.getProjectList();
    },
    /** 查询承包商选择列表 */
    getProjectList() {
      this.projectLoading = true;
      listChoice(this.projectQueryParams).then(response => {
        this.projectList = response.rows;
        this.projectTotal = response.total;
        this.projectLoading = false;
      });
    },
    /** 项目搜索按钮操作 */
    handleProjectQuery() {
      this.projectQueryParams.pageNum = 1;
      this.getProjectList();
    },
    /** 重置项目搜索按钮操作 */
    resetProjectQuery() {
      this.resetForm("projectQueryForm");
      this.handleProjectQuery();
    },
    /** 选择项目 */
    handleSelectProjectRow(row) {
      this.form.projectName = row.projectName;
      this.form.contractorName = row.selectedContractor;
      this.form.choiceId = row.choiceId;
      this.projectSelectOpen = false;
    }
  }
};
</script>
