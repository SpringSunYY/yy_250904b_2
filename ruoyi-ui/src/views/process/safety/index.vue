<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="作业内容是否变化" prop="workContentChanged">
        <el-select v-model="queryParams.workContentChanged" placeholder="请选择作业内容是否变化" clearable>
          <el-option
            v-for="dict in dict.type.sys_yes_no"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="作业地点是否变化" prop="workLocationChanged">
        <el-select v-model="queryParams.workLocationChanged" placeholder="请选择作业地点是否变化" clearable>
          <el-option
            v-for="dict in dict.type.sys_yes_no"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="是否涉及特殊作业" prop="hasSpecialWork">
        <el-select v-model="queryParams.hasSpecialWork" placeholder="请选择是否涉及特殊作业" clearable>
          <el-option
            v-for="dict in dict.type.sys_yes_no"
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
          v-hasPermi="['process:safety:add']"
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
          v-hasPermi="['process:safety:edit']"
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
          v-hasPermi="['process:safety:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['process:safety:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="safetyList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" v-if="columns[0].visible" />
      <el-table-column label="序号" align="center" prop="logId" v-if="columns[1].visible" />
      <el-table-column label="安全日志编号" align="center" prop="logCode" v-if="columns[2].visible" />
      <el-table-column label="项目名称" align="center" prop="projectName" v-if="columns[3].visible" />
      <el-table-column label="承包商" align="center" prop="contractorName" v-if="columns[4].visible" />
      <el-table-column label="安全日志日期" align="center" prop="logDate" width="180" v-if="columns[5].visible">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.logDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="今日工作内容" align="center" prop="todayWorkContent" v-if="columns[6].visible">
        <template slot-scope="scope">
          <span v-html="scope.row.todayWorkContent.replace(/<[^>]+>/g, '')"></span>
        </template>
      </el-table-column>
      <el-table-column label="公司现场负责人" align="center" prop="companySiteManager" v-if="columns[7].visible" />
      <el-table-column label="承包商作业人员数量" align="center" prop="workerCount" v-if="columns[8].visible" />
      <el-table-column label="人员是否经过审查" align="center" prop="workersReviewed" v-if="columns[9].visible">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_yes_no" :value="scope.row.workersReviewed"/>
        </template>
      </el-table-column>
      <el-table-column label="作业内容是否变化" align="center" prop="workContentChanged" v-if="columns[10].visible">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_yes_no" :value="scope.row.workContentChanged"/>
        </template>
      </el-table-column>
      <el-table-column label="作业地点是否变化" align="center" prop="workLocationChanged" v-if="columns[11].visible">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_yes_no" :value="scope.row.workLocationChanged"/>
        </template>
      </el-table-column>
      <el-table-column label="是否涉及特殊作业" align="center" prop="hasSpecialWork" v-if="columns[12].visible">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_yes_no" :value="scope.row.hasSpecialWork"/>
        </template>
      </el-table-column>
      <el-table-column label="有无事故发生" align="center" prop="hasAccident" v-if="columns[13].visible">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_yes_no" :value="scope.row.hasAccident"/>
        </template>
      </el-table-column>
      <el-table-column label="事故简要情况" align="center" prop="accidentBrief" v-if="columns[14].visible" />
      <el-table-column label="与承包商的沟通协调" align="center" prop="communicationCoordination" v-if="columns[15].visible" />
      <el-table-column label="相关附件" align="center" prop="attachments" v-if="columns[16].visible">
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
      <el-table-column label="备注" align="center" prop="notes" v-if="columns[17].visible" />
      <el-table-column label="创建时间" align="center" prop="createdAt" width="180" v-if="columns[18].visible">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createdAt, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" v-if="columns[19].visible">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['process:safety:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['process:safety:remove']"
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

    <!-- 添加或修改安全日志对话框 -->
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
        <el-form-item label="安全日志日期" prop="logDate">
          <el-date-picker clearable
                          v-model="form.logDate"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择安全日志日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="今日工作内容">
          <editor v-model="form.todayWorkContent" :min-height="192"/>
        </el-form-item>
        <el-form-item label="公司现场负责人" prop="companySiteManager">
          <el-input v-model="form.companySiteManager" placeholder="请输入公司现场负责人">
            <el-button slot="append" icon="el-icon-search" @click="handleSelectManager"></el-button>
          </el-input>
        </el-form-item>
        <el-form-item label="承包商作业人员数量" prop="workerCount">
          <el-input v-model="form.workerCount" placeholder="请输入承包商作业人员数量" />
        </el-form-item>
        <el-form-item label="人员是否经过审查" prop="workersReviewed">
          <el-select v-model="form.workersReviewed" placeholder="请选择人员是否经过审查">
            <el-option
              v-for="dict in dict.type.sys_yes_no"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="作业内容是否变化" prop="workContentChanged">
          <el-select v-model="form.workContentChanged" placeholder="请选择作业内容是否变化">
            <el-option
              v-for="dict in dict.type.sys_yes_no"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="作业地点是否变化" prop="workLocationChanged">
          <el-select v-model="form.workLocationChanged" placeholder="请选择作业地点是否变化">
            <el-option
              v-for="dict in dict.type.sys_yes_no"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="是否涉及特殊作业" prop="hasSpecialWork">
          <el-select v-model="form.hasSpecialWork" placeholder="请选择是否涉及特殊作业">
            <el-option
              v-for="dict in dict.type.sys_yes_no"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="有无事故发生" prop="hasAccident">
          <el-select v-model="form.hasAccident" placeholder="请选择有无事故发生">
            <el-option
              v-for="dict in dict.type.sys_yes_no"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="事故简要情况" prop="accidentBrief">
          <el-input v-model="form.accidentBrief" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="与承包商的沟通协调" prop="communicationCoordination">
          <el-input v-model="form.communicationCoordination" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="相关附件" prop="attachments">
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

    <!-- 现场负责人选择对话框 -->
    <el-dialog :title="managerDialogTitle" :visible.sync="managerSelectOpen" width="1100px" append-to-body>
      <el-form :model="managerQueryParams" ref="managerQueryForm" size="small" :inline="true" label-width="68px">
        <el-form-item label="用户名称">
          <el-input
            v-model="managerQueryParams.userName"
            placeholder="请输入用户名称"
            clearable
            @keyup.enter.native="handleManagerQuery"
          />
        </el-form-item>
        <el-form-item label="手机号码">
          <el-input
            v-model="managerQueryParams.phonenumber"
            placeholder="请输入手机号码"
            clearable
            @keyup.enter.native="handleManagerQuery"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleManagerQuery">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetManagerQuery">重置</el-button>
        </el-form-item>
      </el-form>
      <el-table v-loading="managerLoading" :data="managerList" @row-dblclick="handleSelectManagerRow">
        <el-table-column label="用户编号" align="center" prop="userId" />
        <el-table-column label="用户名称" align="center" prop="userName" />
        <el-table-column label="昵称" align="center" prop="nickName" />
        <el-table-column label="部门" align="center" prop="dept.deptName" />
        <el-table-column label="手机号码" align="center" prop="phonenumber" />
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-check"
              @click="handleSelectManagerRow(scope.row)"
            >选择</el-button>
          </template>
        </el-table-column>
      </el-table>

      <pagination
        v-show="managerTotal>0"
        :total="managerTotal"
        :page.sync="managerQueryParams.pageNum"
        :limit.sync="managerQueryParams.pageSize"
        @pagination="getManagerList"
      />

      <div slot="footer" class="dialog-footer">
        <el-button @click="managerSelectOpen = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listSafety, getSafety, delSafety, addSafety, updateSafety } from "@/api/process/safety";
import { listChoice } from "@/api/choice/choice";
import { listUser } from "@/api/system/user";

export default {
  name: "Safety",
  dicts: ['sys_yes_no', 'process_status'],
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
      // 安全日志表格数据
      safetyList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 是否显示项目选择弹出层
      projectSelectOpen: false,
      // 项目选择对话框标题
      projectDialogTitle: "选择项目",
      // 是否显示现场负责人选择弹出层
      managerSelectOpen: false,
      // 现场负责人选择对话框标题
      managerDialogTitle: "选择现场负责人",
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        workContentChanged: null,
        workLocationChanged: null,
        hasSpecialWork: null,
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
      // 现场负责人查询参数
      managerQueryParams: {
        pageNum: 1,
        pageSize: 10,
        userName: null,
        phonenumber: null
      },
      // 现场负责人总条数
      managerTotal: 0,
      // 现场负责人表格数据
      managerList: [],
      // 现场负责人加载状态
      managerLoading: false,
      // 表格列显隐
      columns: [
        { key: 0, visible: true, label: "选择列" },
        { key: 1, visible: true, label: "序号" },
        { key: 2, visible: true, label: "安全日志编号" },
        { key: 3, visible: true, label: "项目名称" },
        { key: 4, visible: true, label: "承包商" },
        { key: 5, visible: true, label: "安全日志日期" },
        { key: 6, visible: true, label: "今日工作内容" },
        { key: 7, visible: true, label: "公司现场负责人" },
        { key: 8, visible: true, label: "承包商作业人员数量" },
        { key: 9, visible: true, label: "人员是否经过审查" },
        { key: 10, visible: true, label: "作业内容是否变化" },
        { key: 11, visible: true, label: "作业地点是否变化" },
        { key: 12, visible: true, label: "是否涉及特殊作业" },
        { key: 13, visible: true, label: "有无事故发生" },
        { key: 14, visible: true, label: "事故简要情况" },
        { key: 15, visible: true, label: "与承包商的沟通协调" },
        { key: 16, visible: true, label: "相关附件" },
        { key: 17, visible: true, label: "备注" },
        { key: 18, visible: true, label: "创建时间" },
        { key: 19, visible: true, label: "操作" }
      ],
      // 表单参数
      form: {},
      // 表单校验
      rules: {
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
    /** 查询安全日志列表 */
    getList() {
      this.loading = true;
      listSafety(this.queryParams).then(response => {
        this.safetyList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
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

    // 选择项目行
    handleSelectProjectRow(row) {
      this.form.projectName = row.projectName;
      this.form.contractorName = row.selectedContractor;
      this.form.choiceId = row.choiceId;
      this.projectSelectOpen = false;
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
        logId: null,
        logCode: null,
        choiceId: null,
        projectName: null,
        contractorName: null,
        logDate: null,
        todayWorkContent: null,
        companySiteManager: null,
        workerCount: null,
        workersReviewed: null,
        workContentChanged: null,
        workLocationChanged: null,
        hasSpecialWork: null,
        specialWorkType: null,
        specialWorkContent: null,
        hasAccident: null,
        accidentBrief: null,
        communicationCoordination: null,
        attachments: null,
        notes: null,
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
      this.ids = selection.map(item => item.logId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加安全日志";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const logId = row.logId || this.ids
      getSafety(logId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改安全日志";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.logId != null) {
            updateSafety(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addSafety(this.form).then(response => {
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
      const logIds = row.logId || this.ids;
      this.$modal.confirm('是否确认删除安全日志编号为"' + logIds + '"的数据项？').then(function() {
        return delSafety(logIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('process/safety/export', {
        ...this.queryParams
      }, `safety_${new Date().getTime()}.xlsx`)
    },
    // 选择现场负责人
    handleSelectManager() {
      this.managerSelectOpen = true;
      this.getManagerList();
    },

    // 查询现场负责人列表
    getManagerList() {
      this.managerLoading = true;
      listUser(this.managerQueryParams).then(response => {
        this.managerList = response.rows;
        this.managerTotal = response.total;
        this.managerLoading = false;
      });
    },

    // 选择现场负责人行
    handleSelectManagerRow(row) {
      this.form.companySiteManager = row.userName;
      this.managerSelectOpen = false;
    },

    // 搜索现场负责人
    handleManagerQuery() {
      this.managerQueryParams.pageNum = 1;
      this.getManagerList();
    },

    // 重置现场负责人查询
    resetManagerQuery() {
      this.managerQueryParams = {
        pageNum: 1,
        pageSize: 10,
        userName: null,
        phonenumber: null
      };
      this.getManagerList();
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
