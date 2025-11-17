<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="项目名称" prop="projectName">
        <el-input
          v-model="queryParams.projectName"
          placeholder="请输入项目名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="拟选承包商" prop="selectedContractor">
        <el-input
          v-model="queryParams.selectedContractor"
          placeholder="请输入拟选承包商"
          clearable
          @keyup.enter.native="handleQuery"
        />
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
          v-hasPermi="['choice:choice:add']"
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
          v-hasPermi="['choice:choice:edit']"
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
          v-hasPermi="['choice:choice:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['choice:choice:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="choiceList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" prop="choiceId" v-if="columns[0].visible" />
      <el-table-column label="项目名称" align="center" prop="projectName" v-if="columns[1].visible" />
      <el-table-column label="拟选承包商" align="center" prop="selectedContractor" v-if="columns[2].visible" />
      <el-table-column label="相关附件" align="center" prop="attachment" v-if="columns[3].visible">
        <template slot-scope="scope">
          <div v-if="scope.row.attachment && scope.row.attachment.length > 0">
            <div v-for="(file, index) in scope.row.attachment.split(',')" :key="index" class="file-item">
              <el-link type="primary" @click="downloadFile(file)">
                <i class="el-icon-document"></i>
                {{ getFileName(file) }}
              </el-link>
            </div>
          </div>
          <span v-else>无</span>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="processStatus" v-if="columns[4].visible">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.process_status" :value="scope.row.processStatus"/>
        </template>
      </el-table-column>
      <!-- 添加审批时间、审批人、审批结果三列 -->
      <el-table-column label="审批时间" align="center" prop="approvalTime" width="180" v-if="columns[5].visible">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.approvalTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="审批人" align="center" prop="approver" v-if="columns[6].visible" />
      <el-table-column label="审批结果" align="center" prop="approvalResult" v-if="columns[7].visible" />
      <el-table-column label="备注" align="center" prop="remarks" v-if="columns[8].visible" />
      <el-table-column label="创建时间" align="center" prop="createdAt" width="180" v-if="columns[9].visible">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createdAt, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" v-if="columns[10].visible">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['choice:choice:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['choice:choice:remove']"
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

    <!-- 添加或修改承包商选择对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="项目名称" prop="projectName">
          <el-input v-model="form.projectName" placeholder="请输入项目名称" readonly>
            <el-button slot="append" icon="el-icon-search" @click="handleSelectProject"></el-button>
          </el-input>
        </el-form-item>
        <el-form-item label="拟选承包商" prop="selectedContractor">
          <el-input v-model="form.selectedContractor" placeholder="请输入拟选承包商" readonly>
            <el-button slot="append" icon="el-icon-search" @click="handleSelectContractor" :disabled="!form.projectId"></el-button>
          </el-input>
        </el-form-item>
        <el-form-item label="相关附件" prop="attachment">
          <file-upload v-model="form.attachment"/>
        </el-form-item>
        <el-form-item label="状态" prop="processStatus">
          <el-select v-model="form.processStatus" placeholder="请选择状态" clearable>
            <el-option
              v-for="dict in dict.type.process_status"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value">
            </el-option>
          </el-select>
        </el-form-item>
        <!-- 新增审批信息 -->
        <el-form-item label="审批时间" prop="approvalTime">
          <el-date-picker
            v-model="form.approvalTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择审批时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="审批人" prop="approver">
          <el-input v-model="form.approver" placeholder="请输入审批人" readonly>
            <el-button slot="append" icon="el-icon-search" @click="handleSelectUser"></el-button>
          </el-input>
        </el-form-item>
        <el-form-item label="审批结果" prop="approvalResult">
          <el-select v-model="form.approvalResult" placeholder="请选择审批结果">
            <el-option label="通过" value="通过"></el-option>
            <el-option label="不通过" value="不通过"></el-option>
          </el-select>
        </el-form-item>
        <!-- 结束新增审批信息 -->
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
    <el-dialog title="选择项目" :visible.sync="projectSelectOpen" width="800px" append-to-body>
      <el-form :model="projectQueryParams" ref="projectQueryForm" size="small" :inline="true" label-width="68px">
        <el-form-item label="项目名称">
          <el-input
            v-model="projectQueryParams.projectName"
            placeholder="请输入项目名称"
            clearable
            @keyup.enter.native="handleProjectQuery"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleProjectQuery">搜索</el-button>
        </el-form-item>
      </el-form>

      <el-table v-loading="projectLoading" :data="projectList" @row-dblclick="handleProjectSelect" highlight-current-row>
        <el-table-column label="项目编号" align="center" prop="projectCode" />
        <el-table-column label="项目名称" align="center" prop="projectName" />
        <el-table-column label="项目类型" align="center" prop="projectType">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.project_type" :value="scope.row.projectType"/>
          </template>
        </el-table-column>
        <el-table-column label="项目预算" align="center" prop="projectBudget" />
        <el-table-column label="项目地点" align="center" prop="projectLocation" />
        <el-table-column label="计划开始日期" align="center" prop="plannedStartDate" width="180">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.plannedStartDate, '{y}-{m}-{d}') }}</span>
          </template>
        </el-table-column>
        <el-table-column label="计划结束日期" align="center" prop="plannedEndDate" width="180">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.plannedEndDate, '{y}-{m}-{d}') }}</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-check"
              @click="handleProjectSelect(scope.row)"
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

    <!-- 合格承包商选择对话框 -->
    <el-dialog title="选择承包商" :visible.sync="contractorSelectOpen" width="800px" append-to-body>
      <el-form :model="contractorQueryParams" ref="contractorQueryForm" size="small" :inline="true" label-width="68px">
        <el-form-item label="承包商名称">
          <el-input
            v-model="contractorQueryParams.contractorName"
            placeholder="请输入承包商名称"
            clearable
            @keyup.enter.native="handleContractorQuery"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleContractorQuery">搜索</el-button>
        </el-form-item>
      </el-form>

      <el-table v-loading="contractorLoading" :data="contractorList" @row-dblclick="handleContractorSelect" highlight-current-row>
        <el-table-column label="承包商名称" align="center" prop="contractorName" />
        <el-table-column label="承揽项目类别" align="center" prop="projectCategories" />
        <el-table-column label="联系人" align="center" prop="contactPerson" />
        <el-table-column label="联系电话" align="center" prop="contactPhone" />
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-check"
              @click="handleContractorSelect(scope.row)"
            >选择</el-button>
          </template>
        </el-table-column>
      </el-table>

      <pagination
        v-show="contractorTotal>0"
        :total="contractorTotal"
        :page.sync="contractorQueryParams.pageNum"
        :limit.sync="contractorQueryParams.pageSize"
        @pagination="getContractorList"
      />

      <div slot="footer" class="dialog-footer">
        <el-button @click="contractorSelectOpen = false">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 用户选择对话框 -->
    <el-dialog title="选择审批人" :visible.sync="userSelectOpen" width="800px" append-to-body>
      <flow-user
        v-if="userSelectOpen"
        :check-type="'single'"
        @handleUserSelect="handleUserSelect">
      </flow-user>
      <div slot="footer" class="dialog-footer">
        <el-button @click="userSelectOpen = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listChoice, getChoice, delChoice, addChoice, updateChoice } from "@/api/choice/choice";
import { listProject } from "@/api/contractor/project";
import { listQualified } from "@/api/contents/qualified";
import FlowUser from "@/components/flow/User";

export default {
  name: "Choice",
  components: {
    FlowUser
  },
  dicts: ['process_status', 'project_type'],
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
      // 承包商选择表格数据
      choiceList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 是否显示项目选择弹出层
      projectSelectOpen: false,
      // 是否显示承包商选择弹出层
      contractorSelectOpen: false,
      // 是否显示用户选择弹出层
      userSelectOpen: false,
      // 列信息
      columns: [
        { key: 0, value: 'choiceId', label: '序号', visible: true },
        { key: 1, value: 'projectName', label: '项目名称', visible: true },
        { key: 2, value: 'selectedContractor', label: '拟选承包商', visible: true },
        { key: 3, value: 'attachment', label: '相关附件', visible: true },
        { key: 4, value: 'processStatus', label: '状态', visible: true },
        { key: 5, value: 'approvalTime', label: '审批时间', visible: true },
        { key: 6, value: 'approver', label: '审批人', visible: true },
        { key: 7, value: 'approvalResult', label: '审批结果', visible: true },
        { key: 8, value: 'remarks', label: '备注', visible: true },
        { key: 9, value: 'createdAt', label: '创建时间', visible: true },
        { key: 10, value: 'operation', label: '操作', visible: true }
      ],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        projectName: null,
        selectedContractor: null,
      },
      // 项目查询参数
      projectQueryParams: {
        pageNum: 1,
        pageSize: 10,
        projectName: null
      },
      // 承包商查询参数
      contractorQueryParams: {
        pageNum: 1,
        pageSize: 10,
        contractorName: null,
        projectType: null
      },
      // 项目总条数
      projectTotal: 0,
      // 项目表格数据
      projectList: [],
      // 项目加载状态
      projectLoading: false,
      // 承包商总条数
      contractorTotal: 0,
      // 承包商表格数据
      contractorList: [],
      // 承包商加载状态
      contractorLoading: false,
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        projectName: [
          { required: true, message: "项目名称不能为空", trigger: "blur" }
        ],
        selectedContractor: [
          { required: true, message: "拟选承包商不能为空", trigger: "blur" }
        ],
        processStatus: [
          { required: true, message: "状态不能为空", trigger: "change" }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询承包商选择列表 */
    getList() {
      this.loading = true;
      listChoice(this.queryParams).then(response => {
        this.choiceList = response.rows;
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
        choiceId: null,
        projectId: null,
        projectName: null,
        qualId: null,
        selectedContractor: null,
        attachment: null,
        processInstanceId: null,
        processStatus: null,
        currentNode: null,
        currentHandler: null,
        remarks: null,
        createdBy: null,
        createdAt: null,
        updatedBy: null,
        updatedAt: null,
        // 新增审批字段
        approvalTime: null,
        approver: null,
        approvalResult: null
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
      this.ids = selection.map(item => item.choiceId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加承包商选择";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const choiceId = row.choiceId || this.ids
      getChoice(choiceId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改承包商选择";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.choiceId != null) {
            updateChoice(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addChoice(this.form).then(response => {
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
      const choiceIds = row.choiceId || this.ids;
      this.$modal.confirm('是否确认删除承包商选择编号为"' + choiceIds + '"的数据项？').then(function() {
        return delChoice(choiceIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('choice/choice/export', {
        ...this.queryParams
      }, `choice_${new Date().getTime()}.xlsx`)
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
    /** 查询发包项目列表 */
    getProjectList() {
      this.projectLoading = true;
      listProject(this.projectQueryParams).then(response => {
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
    /** 选择项目 */
    handleProjectSelect(row) {
      this.form.projectId = row.projectId;
      this.form.projectName = row.projectName;
      // 清除之前选择的承包商
      this.form.qualId = null;
      this.form.selectedContractor = null;
      this.projectSelectOpen = false;
    },
    /** 选择承包商按钮操作 */
    handleSelectContractor() {
      // 添加项目类型筛选条件
      if (this.form.projectId) {
        // 先找到选中的项目信息
        const selectedProject = this.projectList.find(project => project.projectId === this.form.projectId);
        if (selectedProject) {
          this.contractorQueryParams.projectCategories = selectedProject.projectType;
        }
      }
      this.contractorSelectOpen = true;
      this.getContractorList();
    },
    /** 查询合格承包商列表 */
    getContractorList() {
      this.contractorLoading = true;
      listQualified(this.contractorQueryParams).then(response => {
        this.contractorList = response.rows;
        this.contractorTotal = response.total;
        this.contractorLoading = false;
      });
    },
    /** 承包商搜索按钮操作 */
    handleContractorQuery() {
      this.contractorQueryParams.pageNum = 1;
      this.getContractorList();
    },
    /** 选择承包商 */
    handleContractorSelect(row) {
      this.form.qualId = row.qualId;
      this.form.selectedContractor = row.contractorName;
      this.contractorSelectOpen = false;
    },
    /** 选择用户按钮操作 */
    handleSelectUser() {
      this.userSelectOpen = true;
    },
    /** 选择用户 */
    handleUserSelect(selection) {
      if (selection) {
        this.form.approver = selection.nickName;
        this.userSelectOpen = false;
      }
    }
  }
};
</script>
