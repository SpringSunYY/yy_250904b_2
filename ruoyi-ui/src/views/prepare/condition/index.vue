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
          v-hasPermi="['prepare:condition:add']"
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
          v-hasPermi="['prepare:condition:edit']"
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
          v-hasPermi="['prepare:condition:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['prepare:condition:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="conditionList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" prop="conditionId" v-if="columns[0].visible" />
      <el-table-column label="检查编号" align="center" prop="checkCode" v-if="columns[1].visible" />
      <el-table-column label="项目名称" align="center" prop="projectName" v-if="columns[2].visible" />
      <el-table-column label="承包商" align="center" prop="contractorName" v-if="columns[3].visible" />
      <el-table-column label="检查时间" align="center" prop="checkTime" width="180" v-if="columns[4].visible">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.checkTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="检查地点" align="center" prop="checkLocation" v-if="columns[5].visible" />
      <el-table-column label="工程技术措施" align="center" prop="engineeringControlsNotes" v-if="columns[6].visible" />
      <el-table-column label="管理措施" align="center" prop="managementMeasuresNotes" v-if="columns[7].visible" />
      <el-table-column label="培训教育" align="center" prop="trainingEducationNotes" v-if="columns[8].visible" />
      <el-table-column label="个体防护" align="center" prop="personalProtectionNotes" v-if="columns[9].visible" />
      <el-table-column label="应急处置" align="center" prop="emergencyResponseNotes" v-if="columns[10].visible" />
      <el-table-column label="总体评价" align="center" prop="overallAssessment" v-if="columns[11].visible">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.overall_assessment" :value="scope.row.overallAssessment"/>
        </template>
      </el-table-column>
      <el-table-column label="已识别风险" align="center" prop="identifiedRisks" v-if="columns[12].visible" />
      <el-table-column label="整改措施" align="center" prop="correctiveActions" v-if="columns[13].visible" />
      <el-table-column label="整改期限" align="center" prop="rectificationDeadline" width="180" v-if="columns[14].visible">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.rectificationDeadline, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="后续要求" align="center" prop="followUpRequirements" v-if="columns[15].visible" />
      <el-table-column label="检查记录文件" align="center" prop="checkDocument" v-if="columns[16].visible">
        <template slot-scope="scope">
          <div v-if="scope.row.checkDocument && scope.row.checkDocument.length > 0">
            <div v-for="(file, index) in scope.row.checkDocument.split(',')" :key="index" class="file-item">
              <el-link type="primary" @click="downloadFile(file)">
                <i class="el-icon-document"></i>
                {{ getFileName(file) }}
              </el-link>
            </div>
          </div>
          <span v-else>无</span>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remarks" v-if="columns[17].visible" />
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
            v-hasPermi="['prepare:condition:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['prepare:condition:remove']"
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

    <!-- 添加或修改安全条件检查对话框 -->
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
        <el-form-item label="检查时间" prop="checkTime">
          <el-date-picker clearable
            v-model="form.checkTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择检查时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="检查地点" prop="checkLocation">
          <el-input v-model="form.checkLocation" placeholder="请输入检查地点" />
        </el-form-item>
        <el-form-item label="工程技术措施" prop="engineeringControlsNotes">
          <el-input v-model="form.engineeringControlsNotes" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="管理措施" prop="managementMeasuresNotes">
          <el-input v-model="form.managementMeasuresNotes" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="培训教育" prop="trainingEducationNotes">
          <el-input v-model="form.trainingEducationNotes" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="个体防护" prop="personalProtectionNotes">
          <el-input v-model="form.personalProtectionNotes" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="应急处置" prop="emergencyResponseNotes">
          <el-input v-model="form.emergencyResponseNotes" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="总体评价" prop="overallAssessment">
          <el-select v-model="form.overallAssessment" placeholder="请选择总体评价">
            <el-option
              v-for="dict in dict.type.overall_assessment"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="已识别风险" prop="identifiedRisks">
          <el-input v-model="form.identifiedRisks" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="整改措施" prop="correctiveActions">
          <el-input v-model="form.correctiveActions" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="整改期限" prop="rectificationDeadline">
          <el-date-picker clearable
            v-model="form.rectificationDeadline"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择整改期限">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="后续要求" prop="followUpRequirements">
          <el-input v-model="form.followUpRequirements" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="检查记录文件" prop="checkDocument">
          <file-upload v-model="form.checkDocument"/>
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
import { listCondition, getCondition, delCondition, addCondition, updateCondition } from "@/api/prepare/condition";
import { listChoice } from "@/api/choice/choice";

export default {
  name: "Condition",
  dicts: ['overall_assessment', 'process_status'],
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
      // 安全条件检查表格数据
      conditionList: [],
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
        { key: 1, label: `检查编号`, visible: true },
        { key: 2, label: `项目名称`, visible: true },
        { key: 3, label: `承包商`, visible: true },
        { key: 4, label: `检查时间`, visible: true },
        { key: 5, label: `检查地点`, visible: true },
        { key: 6, label: `工程技术措施`, visible: true },
        { key: 7, label: `管理措施`, visible: true },
        { key: 8, label: `培训教育`, visible: true },
        { key: 9, label: `个体防护`, visible: true },
        { key: 10, label: `应急处置`, visible: true },
        { key: 11, label: `总体评价`, visible: true },
        { key: 12, label: `已识别风险`, visible: true },
        { key: 13, label: `整改措施`, visible: true },
        { key: 14, label: `整改期限`, visible: true },
        { key: 15, label: `后续要求`, visible: true },
        { key: 16, label: `检查记录文件`, visible: true },
        { key: 17, label: `备注`, visible: true },
        { key: 18, label: `创建时间`, visible: true },
        { key: 19, label: `操作`, visible: true }
      ],
      // 表单校验
      rules: {
        checkCode: [
          { required: true, message: "检查编号不能为空", trigger: "blur" }
        ],
        projectName: [
          { required: true, message: "项目名称不能为空", trigger: "blur" }
        ],
        contractorName: [
          { required: true, message: "承包商不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询安全条件检查列表 */
    getList() {
      this.loading = true;
      listCondition(this.queryParams).then(response => {
        this.conditionList = response.rows;
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
        conditionId: null,
        checkCode: null,
        choiceId: null,
        projectName: null,
        contractorName: null,
        checkTime: null,
        checkLocation: null,
        engineeringControlsNotes: null,
        managementMeasuresNotes: null,
        trainingEducationNotes: null,
        personalProtectionNotes: null,
        emergencyResponseNotes: null,
        overallAssessment: null,
        identifiedRisks: null,
        correctiveActions: null,
        rectificationDeadline: null,
        followUpRequirements: null,
        checkDocument: null,
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
      this.ids = selection.map(item => item.conditionId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加安全条件检查";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const conditionId = row.conditionId || this.ids
      getCondition(conditionId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改安全条件检查";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.conditionId != null) {
            updateCondition(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addCondition(this.form).then(response => {
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
      const conditionIds = row.conditionId || this.ids;
      this.$modal.confirm('是否确认删除安全条件检查编号为"' + conditionIds + '"的数据项？').then(function() {
        return delCondition(conditionIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('prepare/condition/export', {
        ...this.queryParams
      }, `condition_${new Date().getTime()}.xlsx`)
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
    }
  }
};
</script>
