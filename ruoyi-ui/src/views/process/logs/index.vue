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
          v-hasPermi="['process:logs:add']"
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
          v-hasPermi="['process:logs:edit']"
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
          v-hasPermi="['process:logs:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['process:logs:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="logsList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" v-if="columns[0].visible" />
      <el-table-column label="序号" align="center" prop="logsId" v-if="columns[1].visible" />
      <el-table-column label="日志编号" align="center" prop="logsCode" v-if="columns[2].visible" />
      <el-table-column label="项目名称" align="center" prop="projectName" v-if="columns[3].visible" />
      <el-table-column label="承包商" align="center" prop="contractorName" v-if="columns[4].visible" />
      <el-table-column label="日志日期" align="center" prop="logsDate" width="180" v-if="columns[5].visible">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.logsDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="今日工作内容" align="center" prop="todayWorkContent" v-if="columns[6].visible" />
      <el-table-column label="作业地点/区域" align="center" prop="workLocation" v-if="columns[7].visible" />
      <el-table-column label="承包商现场负责人" align="center" prop="contractorSiteManager" v-if="columns[8].visible" />
      <el-table-column label="安全管理人员" align="center" prop="safetyManager" v-if="columns[9].visible" />
      <el-table-column label="作业人员" align="center" prop="workersInfo" v-if="columns[10].visible" />
      <el-table-column label="使用的工器具" align="center" prop="toolsEquipment" v-if="columns[11].visible" />
      <el-table-column label="遇到的问题" align="center" prop="issuesEncountered" v-if="columns[12].visible" />
      <el-table-column label="需要协调事项" align="center" prop="coordinationNeeds" v-if="columns[13].visible" />
      <el-table-column label="备注" align="center" prop="notes" v-if="columns[14].visible" />
      <el-table-column label="创建时间" align="center" prop="createdAt" width="180" v-if="columns[15].visible">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createdAt, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" v-if="columns[16].visible">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['process:logs:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['process:logs:remove']"
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

    <!-- 添加或修改项目日志对话框 -->
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
        <el-form-item label="日志日期" prop="logsDate">
          <el-date-picker clearable
            v-model="form.logsDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择日志日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="今日工作内容">
          <editor v-model="form.todayWorkContent" :min-height="192"/>
        </el-form-item>
        <el-form-item label="作业地点/区域" prop="workLocation">
          <el-input v-model="form.workLocation" placeholder="请输入作业地点/区域" />
        </el-form-item>
        <el-form-item label="承包商现场负责人" prop="contractorSiteManager">
          <el-select
            v-model="form.contractorSiteManager"
            placeholder="请选择承包商现场负责人"
            clearable
            filterable
            @focus="loadContractorPersonnel"
          >
            <el-option
              v-for="person in contractorPersonnelList"
              :key="person.personnelId"
              :label="person.personnelName"
              :value="person.personnelName">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="安全管理人员" prop="safetyManager">
          <el-select
            v-model="form.safetyManager"
            placeholder="请选择安全管理人员"
            clearable
            filterable
            @focus="loadContractorPersonnel"
          >
            <el-option
              v-for="person in contractorPersonnelList"
              :key="person.personnelId"
              :label="person.personnelName"
              :value="person.personnelName">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="作业人员" prop="workersInfo">
          <el-select
            v-model="form.workersInfo"
            placeholder="请选择作业人员"
            clearable
            filterable
            multiple
            @focus="loadContractorPersonnel"
          >
            <el-option
              v-for="person in contractorPersonnelList"
              :key="person.personnelId"
              :label="person.personnelName"
              :value="person.personnelName">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="使用的工器具" prop="toolsEquipment">
          <el-select
            v-model="form.toolsEquipment"
            placeholder="请选择使用的工器具"
            clearable
            filterable
            multiple
            @focus="loadContractorTools"
          >
            <el-option
              v-for="tool in contractorToolsList"
              :key="tool.toolId"
              :label="tool.toolName"
              :value="tool.toolName">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="遇到的问题" prop="issuesEncountered">
          <el-input v-model="form.issuesEncountered" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="需要协调事项" prop="coordinationNeeds">
          <el-input v-model="form.coordinationNeeds" type="textarea" placeholder="请输入内容" />
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
  </div>
</template>

<script>
import { listLogs, getLogs, delLogs, addLogs, updateLogs } from "@/api/process/logs";
import { listChoice } from "@/api/choice/choice";
import { listRegister } from "@/api/personnel/register";
import { listInventory } from "@/api/prepare/inventory";

export default {
  name: "Logs",
  dicts: ['process_status'],
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
      // 项目日志表格数据
      logsList: [],
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
      // 承包商人员列表
      contractorPersonnelList: [],
      // 承包商工器具列表
      contractorToolsList: [],
      // 表单参数
      form: {},
      // 表格列显隐
      columns: [
        { key: 0, visible: true, label: "选择列" },
        { key: 1, visible: true, label: "序号" },
        { key: 2, visible: true, label: "日志编号" },
        { key: 3, visible: true, label: "项目名称" },
        { key: 4, visible: true, label: "承包商" },
        { key: 5, visible: true, label: "日志日期" },
        { key: 6, visible: true, label: "今日工作内容" },
        { key: 7, visible: true, label: "作业地点/区域" },
        { key: 8, visible: true, label: "承包商现场负责人" },
        { key: 9, visible: true, label: "安全管理人员" },
        { key: 10, visible: true, label: "作业人员" },
        { key: 11, visible: true, label: "使用的工器具" },
        { key: 12, visible: true, label: "遇到的问题" },
        { key: 13, visible: true, label: "需要协调事项" },
        { key: 14, visible: true, label: "备注" },
        { key: 15, visible: true, label: "创建时间" },
        { key: 16, visible: true, label: "操作" }
      ],
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
    /** 查询项目日志列表 */
    getList() {
      this.loading = true;
      listLogs(this.queryParams).then(response => {
        this.logsList = response.rows;
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
      // 当选择承包商后，加载该承包商的人员信息
      this.loadContractorPersonnel();
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

    // 加载承包商人员信息
    loadContractorPersonnel() {
      if (this.form.contractorName) {
        const queryParams = {
          contractorName: this.form.contractorName
        };
        listRegister(queryParams).then(response => {
          this.contractorPersonnelList = response.rows;
        });
      }
    },

    // 加载承包商工器具信息
    loadContractorTools() {
      if (this.form.contractorName) {
        const queryParams = {
          contractorName: this.form.contractorName
        };
        listInventory(queryParams).then(response => {
          this.contractorToolsList = response.rows;
        });
      }
    },

    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        logsId: null,
        logsCode: null,
        choiceId: null,
        projectName: null,
        contractorName: null,
        logsDate: null,
        todayWorkContent: null,
        workLocation: null,
        contractorSiteManager: null,
        safetyManager: null,
        workersInfo: null,
        toolId: null,
        toolsEquipment: null,
        issuesEncountered: null,
        coordinationNeeds: null,
        notes: null,
        createdBy: null,
        createdAt: null,
        updatedBy: null,
        updatedAt: null
      };
      this.contractorPersonnelList = [];
      this.contractorToolsList = [];
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
      this.ids = selection.map(item => item.logsId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加项目日志";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const logsId = row.logsId || this.ids
      getLogs(logsId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改项目日志";
        // 加载承包商人员信息
        this.loadContractorPersonnel();
        // 加载承包商工器具信息
        this.loadContractorTools();
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          // 处理多选的作业人员数据，将其转换为字符串存储
          let form_data = Object.assign({}, this.form);
          if (Array.isArray(form_data.workersInfo)) {
            form_data.workersInfo = form_data.workersInfo.join(',');
          }

          // 处理多选的工器具数据，将其转换为字符串存储
          if (Array.isArray(form_data.toolsEquipment)) {
            form_data.toolsEquipment = form_data.toolsEquipment.join(',');
          }

          // 去除今日工作内容中的<p>标签
          if (form_data.todayWorkContent) {
            form_data.todayWorkContent = form_data.todayWorkContent.replace(/<p>/g, '').replace(/<\/p>/g, '');
          }

          if (this.form.logsId != null) {
            updateLogs(form_data).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addLogs(form_data).then(response => {
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
      const logsIds = row.logsId || this.ids;
      this.$modal.confirm('是否确认删除项目日志编号为"' + logsIds + '"的数据项？').then(function() {
        return delLogs(logsIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('process/logs/export', {
        ...this.queryParams
      }, `logs_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
