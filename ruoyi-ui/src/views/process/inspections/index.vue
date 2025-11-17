<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="有无隐患" prop="hasHazards">
        <el-select v-model="queryParams.hasHazards" placeholder="请选择有无隐患" clearable>
          <el-option
            v-for="dict in dict.type.has_hazards"
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
          v-hasPermi="['process:inspections:add']"
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
          v-hasPermi="['process:inspections:edit']"
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
          v-hasPermi="['process:inspections:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['process:inspections:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="inspectionsList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" v-if="columns[0].visible" />
      <el-table-column label="序号" align="center" prop="id" v-if="columns[1].visible" />
      <el-table-column label="检查记录编号" align="center" prop="inspectionCode" v-if="columns[2].visible" />
      <el-table-column label="项目名称" align="center" prop="projectName" v-if="columns[3].visible" />
      <el-table-column label="承包商" align="center" prop="contractorName" v-if="columns[4].visible" />
      <el-table-column label="检查时间" align="center" prop="inspectionDatetime" width="180" v-if="columns[5].visible">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.inspectionDatetime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="天气情况" align="center" prop="weatherCondition" v-if="columns[6].visible" />
      <el-table-column label="作业地点/区域" align="center" prop="workLocation" v-if="columns[7].visible" />
      <el-table-column label="检查人员" align="center" prop="inspectorName" v-if="columns[8].visible" />
      <el-table-column label="承包商陪同人员" align="center" prop="contractorAttendant" v-if="columns[9].visible">
        <template slot-scope="scope">
          <span>{{ scope.row.contractorAttendant }}</span>
        </template>
      </el-table-column>
      <el-table-column label="有无隐患" align="center" prop="hasHazards" v-if="columns[10].visible">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.has_hazards" :value="scope.row.hasHazards"/>
        </template>
      </el-table-column>
      <el-table-column label="隐患数量" align="center" prop="hazardsCount" v-if="columns[11].visible" />
      <el-table-column label="检查记录" align="center" prop="attachments" v-if="columns[12].visible">
        <template slot-scope="scope">
          <div v-if="scope.row.attachments && scope.row.attachments.length > 0">
            <div v-for="(file, index) in scope.row.attachments.split(',')" :key="index" class="file-item">
              <el-link type="primary" @click="downloadFile(file)">
                <i class="el-icon-document"></i>
                {{ file }}
              </el-link>
            </div>
          </div>
          <span v-else>无</span>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="notes" v-if="columns[13].visible" />
      <el-table-column label="创建时间" align="center" prop="createdAt" width="180" v-if="columns[14].visible">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createdAt, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" v-if="columns[15].visible">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['process:inspections:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['process:inspections:remove']"
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

    <!-- 添加或修改监督检查记录对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="项目名称" prop="projectName">
          <el-input v-model="form.projectName" placeholder="请输入项目名称">
            <el-button slot="append" icon="el-icon-search" @click="handleSelectProject"></el-button>
          </el-input>
        </el-form-item>
        <el-form-item label="承包商"prop="contractorName">
          <el-input
            v-model="form.contractorName"
            placeholder="请输入承包商"
            @blur="getContractorPersonnelList(form.contractorName)"
          />
        </el-form-item>
        <el-form-item label="检查时间" prop="inspectionDatetime">
          <el-date-picker clearable
                          v-model="form.inspectionDatetime"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择检查时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="天气情况" prop="weatherCondition">
          <el-input v-model="form.weatherCondition" placeholder="请输入天气情况" />
        </el-form-item>
        <el-form-item label="作业地点/区域" prop="workLocation">
          <el-input v-model="form.workLocation" placeholder="请输入作业地点/区域" />
        </el-form-item>
        <el-form-item label="检查人员" prop="inspectorName">
          <el-input v-model="form.inspectorName" placeholder="请选择或输入检查人员">
            <el-button slot="append" icon="el-icon-search" @click="handleSelectUser"></el-button>
          </el-input>
          <div v-if="selectedInspectors.length > 0" style="margin-top: 5px;">
            <el-tag
              v-for="(inspector, index) in selectedInspectors"
              :key="index"
              closable
              @close="removeInspector(index)"
              style="margin-right: 5px;"
            >
              {{ inspector }}
            </el-tag>
          </div>
        </el-form-item>
        <el-form-item label="承包商陪同人员"prop="contractorAttendant">
          <el-select
            v-model="contractorAttendantArray"
            placeholder="请选择承包商陪同人员"
            clearable
            filterable
            multiple
          >
            <el-option
              v-for="person in contractorPersonnelList"
              :key="person.personnelId"
              :label="person.personnelName"
              :value="person.personnelName">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="有无隐患" prop="hasHazards">
          <el-select v-model="form.hasHazards" placeholder="请选择有无隐患">
            <el-option
              v-for="dict in dict.type.has_hazards"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="隐患数量" prop="hazardsCount">
          <el-input v-model="form.hazardsCount" placeholder="请输入隐患数量" />
        </el-form-item>
        <el-form-item label="检查记录" prop="attachments">
          <file-upload v-model="form.attachments"/>
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

    <!-- 用户选择对话框 -->
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
        <el-table-column label="用户名称" align="center" prop="userName" />
        <el-table-column label="用户昵称" align="center" prop="nickName" />
        <el-table-column label="部门" align="center" prop="dept.deptName" />
        <el-table-column label="手机号码" align="center" prop="phonenumber" />
        <el-table-column label="状态" align="center" prop="status">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.sys_normal_disable" :value="scope.row.status"/>
          </template>
        </el-table-column>
        <el-table-column label="创建时间" align="center" prop="createTime" width="180">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.createTime) }}</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-check"
              @click="handleSelectUserRow(scope.row)"
            >添加</el-button>
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
import { listInspections, getInspections, delInspections, addInspections, updateInspections } from "@/api/process/inspections";
import { listChoice } from "@/api/choice/choice";
import { listUser } from "@/api/system/user";
import { listRegister } from "@/api/personnel/register";

export default {
  name: "Inspections",
  dicts: ['has_hazards', 'process_status', 'sys_normal_disable'],
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
      // 监督检查记录表格数据
      inspectionsList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 是否显示项目选择弹出层
      projectSelectOpen: false,
      // 项目选择对话框标题
      projectDialogTitle: "选择项目",
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        projectName: null,
        contractorName: null,
        hasHazards: null,
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
      // 表格列显隐
      columns: [
        { key: 0, visible: true, label: "选择列" },
        { key: 1, visible: true, label: "序号" },
        { key: 2, visible: true, label: "检查记录编号" },
        { key: 3, visible: true, label: "项目名称" },
        { key: 4, visible: true, label: "承包商" },
        { key: 5, visible: true, label: "检查时间" },
        { key: 6, visible: true, label: "天气情况" },
        { key: 7, visible: true, label: "作业地点/区域" },
        { key: 8, visible: true, label: "检查人员" },
        { key: 9, visible: true, label: "承包商陪同人员" },
        { key: 10, visible: true, label: "有无隐患" },
        { key: 11, visible: true, label: "隐患数量" },
        { key: 12, visible: true, label: "检查记录" },
        { key: 13, visible: true, label: "备注" },
        { key: 14, visible: true, label: "创建时间" },
        { key: 15, visible: true, label: "操作" }
      ],
      // 承包商陪同人员数组（用于多选）
      contractorAttendantArray: [],
      // 已选择的检查人员
      selectedInspectors: [],
      // 承包商人员列表
      contractorPersonnelList: [],
      // 用户表格数据
      userList: [],
      // 用户查询参数
      userQueryParams: {
        pageNum: 1,
        pageSize: 10,
        userName: undefined,
        phonenumber: undefined
      },
      // 用户总条数
      userTotal: 0,
      // 用户加载状态
      userLoading: false,
      // 是否显示用户选择弹出层
      userSelectOpen: false,
      // 用户选择对话框标题
      userDialogTitle: "选择用户",
      // 表单校验
      rules: {
        id: [
          { required: true, message: "序号不能为空", trigger: "blur" }
        ],
        projectName: [
          { required: true, message: "项目名称不能为空", trigger: "blur" }
        ],
        contractorName: [
          { required: true, message: "承包商不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询监督检查记录列表 */
    getList() {
      this.loading = true;
      listInspections(this.queryParams).then(response => {
        this.inspectionsList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 选择用户
    handleSelectUser() {
      this.userSelectOpen = true;
      this.getUserList();
    },

    // 查询用户列表
    getUserList() {
      this.userLoading = true;
      listUser(this.userQueryParams).then(response => {
        this.userList = response.rows;
        this.userTotal = response.total;
        this.userLoading = false;
      });
    },

    // 查询承包商人员列表
    getContractorPersonnelList(contractorName) {
      if (contractorName) {
        listRegister({ contractorName: contractorName }).then(response => {
          this.contractorPersonnelList = response.rows;
        });
      }
    },

    // 选择项目行
    handleSelectProjectRow(row) {
      this.form.projectName = row.projectName;
      this.form.contractorName = row.selectedContractor;
      this.form.choiceId = row.choiceId;
      // 加载该承包商的人员信息
      this.getContractorPersonnelList(this.form.contractorName);
      this.projectSelectOpen = false;
    },

    // 选择用户行（多选）
    handleSelectUserRow(row) {
      // 检查是否已选择该用户
      if (!this.selectedInspectors.includes(row.userName)) {
        this.selectedInspectors.push(row.userName);
        this.form.inspectorName = this.selectedInspectors.join(', ');
      }
      this.userSelectOpen = false;
    },

    // 搜索用户
    handleUserQuery() {
      this.userQueryParams.pageNum = 1;
      this.getUserList();
    },

    // 重置用户查询
    resetUserQuery() {
      this.userQueryParams = {
        pageNum: 1,
        pageSize: 10,
        userName: undefined,
        phonenumber: undefined
      };
      this.getUserList();
    },
    // 选择项目
    handleSelectProject() {
      this.projectSelectOpen = true;
      this.getProjectList();
    },

    // 查询项目列表
    getProjectList() {
      this.projectLoading = true;
      listChoice(this.projectQueryParams).then(response => {
        this.projectList = response.rows;
        this.projectTotal = response.total;
        this.projectLoading = false;
      });
    },

    // 搜索项目
    handleProjectQuery() {
      this.projectQueryParams.pageNum = 1;
      this.getProjectList();
    },

    // 重置项目查询
    resetProjectQuery() {
      this.projectQueryParams = {
        pageNum: 1,
        pageSize: 10,
        projectName: null,
        selectedContractor: null
      };
      this.getProjectList();
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        inspectionCode: null,
        choiceId: null,
        projectName: null,
        contractorName: null,
        inspectionDatetime: null,
        weatherCondition: null,
        workLocation: null,
        inspectorName: null,
        contractorAttendant: null,
        hasHazards: null,
        hazardsCount: null,
        attachments: null,
        notes: null,
        createdBy: null,
        createdAt: null,
        updatedBy: null,
        updatedAt: null
      };
      this.contractorAttendantArray = [];
      this.selectedInspectors = [];
      this.contractorPersonnelList = [];
      this.resetForm("form");
    },
    // 移除已选择的检查人员
    removeInspector(index) {
      this.selectedInspectors.splice(index, 1);
      this.form.inspectorName = this.selectedInspectors.join(', ');
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
      this.title = "添加监督检查记录";
      // 加载用户列表数据用于检查人员选择
      this.getUserList();
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getInspections(id).then(response => {
        this.form = response.data;
        // 处理承包商陪同人员数据
        if (this.form.contractorAttendant) {
          this.contractorAttendantArray = this.form.contractorAttendant.split(',').map(item => item.trim()).filter(item => item !== '');
        } else {
          this.contractorAttendantArray = [];
        }
        this.open = true;
        this.title = "修改监督检查记录";
        // 加载用户列表数据用于检查人员选择
        this.getUserList();
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          // 处理承包商陪同人员数据，将数组转换为逗号分隔的字符串
          if (this.contractorAttendantArray && this.contractorAttendantArray.length > 0) {
            this.form.contractorAttendant = this.contractorAttendantArray.join(', ');
          } else {
            this.form.contractorAttendant = '';
          }

          if (this.form.id != null) {
            updateInspections(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addInspections(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除监督检查记录编号为"' + ids + '"的数据项？').then(function() {
        return delInspections(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('process/inspections/export', {
        ...this.queryParams
      }, `inspections_${new Date().getTime()}.xlsx`)
    },
    /**
     * 下载文件
     */
    downloadFile(filename) {
      this.$confirm(`确定要下载文件 "${filename}" 吗？`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // 构建下载URL
        const downloadUrl = `/api/common/download?fileName=${encodeURIComponent(filename)}`;

        // 创建隐藏的a标签进行下载
        const link = document.createElement('a');
        link.href = downloadUrl;
        link.download = filename;
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消下载'
        });
      });
    },
  }
};
</script>
