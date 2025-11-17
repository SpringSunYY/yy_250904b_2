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
      <el-form-item label="承包商" prop="contractorName">
        <el-input
          v-model="queryParams.contractorName"
          placeholder="请输入承包商"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="变更类型" prop="changeType">
        <el-select v-model="queryParams.changeType" placeholder="请选择变更类型" clearable>
          <el-option
            v-for="dict in dict.type.change_type"
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
          v-hasPermi="['process:change:add']"
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
          v-hasPermi="['process:change:edit']"
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
          v-hasPermi="['process:change:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['process:change:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="changeList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" v-if="columns[0].visible" />
      <el-table-column label="序号" align="center" prop="changeId" v-if="columns[1].visible" />
      <el-table-column label="变更记录编号" align="center" prop="changeNo" v-if="columns[2].visible" />
      <el-table-column label="项目名称" align="center" prop="projectName" v-if="columns[3].visible" />
      <el-table-column label="承包商" align="center" prop="contractorName" v-if="columns[4].visible" />
      <el-table-column label="变更申请日期" align="center" prop="changeTime" width="180" v-if="columns[5].visible">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.changeTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="变更类型" align="center" prop="changeType" v-if="columns[6].visible">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.change_type" :value="scope.row.changeType"/>
        </template>
      </el-table-column>
      <el-table-column label="变更原因" align="center" prop="changeReason" v-if="columns[7].visible" />
      <el-table-column label="变更记录" align="center" prop="changeRecord" v-if="columns[8].visible">
        <template slot-scope="scope">
          <div v-if="scope.row.changeRecord && scope.row.changeRecord.length > 0">
            <div v-for="(file, index) in scope.row.changeRecord.split(',')" :key="index" class="file-item">
              <el-link type="primary" @click="downloadFile(file)">
                <i class="el-icon-document"></i>
                {{ file}}
              </el-link>
            </div>
          </div>
          <span v-else>无</span>
        </template>
      </el-table-column>
      <el-table-column label="安全管理协议是否修改" align="center" prop="safetyProtocolUpdated" v-if="columns[9].visible">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_yes_no" :value="scope.row.safetyProtocolUpdated"/>
        </template>
      </el-table-column>
      <el-table-column label="安全管理协议修订文本" align="center"prop="safetyProtocolAttachment" v-if="columns[10].visible">
        <template slot-scope="scope">
          <div v-if="scope.row.safetyProtocolAttachment && scope.row.safetyProtocolAttachment.length > 0">
            <div v-for="(file, index) in scope.row.safetyProtocolAttachment.split(',')" :key="index" class="file-item">
              <el-link type="primary" @click="downloadFile(file)">
                <i class="el-icon-document"></i>
                {{file}}
              </el-link>
            </div>
          </div>
          <span v-else>无</span>
        </template>
      </el-table-column>
      <el-table-column label="施工方案是否修改" align="center" prop="constructionPlanUpdated" v-if="columns[11].visible">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_yes_no" :value="scope.row.constructionPlanUpdated"/>
        </template>
      </el-table-column>
      <el-table-column label="工程施工方案修订文本" align="center"prop="constructionPlanAttachment" v-if="columns[12].visible">
        <template slot-scope="scope">
          <div v-if="scope.row.constructionPlanAttachment && scope.row.constructionPlanAttachment.length > 0">
            <div v-for="(file, index) in scope.row.constructionPlanAttachment.split(',')" :key="index" class="file-item">
              <el-link type="primary" @click="downloadFile(file)">
                <i class="el-icon-document"></i>
                {{file}}
              </el-link>
            </div>
          </div>
          <span v-else>无</span>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" v-if="columns[13].visible" />
      <el-table-column label="创建时间" align="center" prop="createdTime" width="180" v-if="columns[14].visible">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createdTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" v-if="columns[15].visible">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['process:change:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['process:change:remove']"
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

    <!-- 添加或修改项目变更管理对话框 -->
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
        <el-form-item label="变更申请日期" prop="changeTime">
          <el-date-picker clearable
            v-model="form.changeTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择变更申请日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="变更类型" prop="changeType">
          <el-select v-model="form.changeType" placeholder="请选择变更类型">
            <el-option
              v-for="dict in dict.type.change_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="变更原因" prop="changeReason">
          <el-input v-model="form.changeReason" placeholder="请输入变更原因" />
        </el-form-item>
        <el-form-item label="变更记录" prop="changeRecord">
          <file-upload v-model="form.changeRecord"/>
        </el-form-item>
        <el-form-item label="安全管理协议是否修改" prop="safetyProtocolUpdated">
          <el-select v-model="form.safetyProtocolUpdated" placeholder="请选择安全管理协议是否修改">
            <el-option
              v-for="dict in dict.type.sys_yes_no"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="安全管理协议修订文本" prop="safetyProtocolAttachment">
          <file-upload v-model="form.safetyProtocolAttachment"/>
        </el-form-item>
        <el-form-item label="施工方案是否修改" prop="constructionPlanUpdated">
          <el-select v-model="form.constructionPlanUpdated" placeholder="请选择施工方案是否修改">
            <el-option
              v-for="dict in dict.type.sys_yes_no"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="施工方案修订文本" prop="constructionPlanAttachment">
          <file-upload v-model="form.constructionPlanAttachment"/>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" placeholder="请输入备注" />
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
import { listChange, getChange, delChange, addChange, updateChange } from "@/api/process/change";
import { listChoice } from "@/api/choice/choice";

export default {
  name: "Change",
  dicts: ['change_type', 'sys_yes_no', 'process_status'],
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
      // 项目变更管理表格数据
      changeList: [],
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
        changeType: null,
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
      // 表格列显隐
      columns: [
        { key: 0, visible: true, label: "选择列" },
        { key: 1, visible: true, label: "序号" },
        { key: 2, visible: true, label: "变更记录编号" },
        { key: 3, visible: true, label: "项目名称" },
        { key: 4, visible: true, label: "承包商" },
        { key: 5, visible: true, label: "变更申请日期" },
        { key: 6, visible: true, label: "变更类型" },
        { key: 7, visible: true, label: "变更原因" },
        { key: 8, visible: true, label: "变更记录" },
        { key: 9, visible: true, label: "安全管理协议是否修改" },
        { key: 10, visible: true, label: "安全管理协议修订文本" },
        { key: 11, visible: true, label: "施工方案是否修改" },
        { key: 12, visible: true, label: "工程施工方案修订文本" },
        { key: 13, visible: true, label: "备注" },
        { key: 14, visible: true, label: "创建时间" },
        { key: 15, visible: true, label: "操作" }
      ],
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        changeId: [
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
    /** 查询项目变更管理列表 */
    getList() {
      this.loading = true;
      listChange(this.queryParams).then(response => {
        this.changeList = response.rows;
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
        changeId: null,
        changeNo: null,
        choiceId: null,
        projectName: null,
        contractorName: null,
        changeTime: null,
        changeType: null,
        changeReason: null,
        changeRecord: null,
        safetyProtocolUpdated: null,
        safetyProtocolAttachment: null,
        constructionPlanUpdated: null,
        constructionPlanAttachment: null,
        remark: null,
        createdBy: null,
        createdTime: null,
        updatedBy: null,
        updatedTime: null
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
      this.ids = selection.map(item => item.changeId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加项目变更管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const changeId = row.changeId || this.ids
      getChange(changeId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改项目变更管理";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.changeId != null) {
            updateChange(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addChange(this.form).then(response => {
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
      const changeIds = row.changeId || this.ids;
      this.$modal.confirm('是否确认删除项目变更管理编号为"' + changeIds + '"的数据项？').then(function() {
        return delChange(changeIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('process/change/export', {
        ...this.queryParams
      }, `change_${new Date().getTime()}.xlsx`)
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
