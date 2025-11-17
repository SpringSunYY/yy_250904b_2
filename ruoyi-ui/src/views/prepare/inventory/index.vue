<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="工器具名称" prop="toolName">
        <el-input
          v-model="queryParams.toolName"
          placeholder="请输入工器具名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="工器具类型" prop="toolType">
        <el-select v-model="queryParams.toolType" placeholder="请选择工器具类型" clearable>
          <el-option
            v-for="dict in dict.type.tool_type"
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
          v-hasPermi="['prepare:inventory:add']"
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
          v-hasPermi="['prepare:inventory:edit']"
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
          v-hasPermi="['prepare:inventory:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['prepare:inventory:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="inventoryList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" prop="toolId" v-if="columns[0].visible" />
      <el-table-column label="工器具编号" align="center" prop="toolCode" v-if="columns[1].visible" />
      <el-table-column label="项目名称" align="center" prop="projectName" v-if="columns[2].visible" />
      <el-table-column label="承包商" align="center" prop="contractorName" v-if="columns[3].visible" />
      <el-table-column label="工器具名称" align="center" prop="toolName" v-if="columns[4].visible" />
      <el-table-column label="工器具类型" align="center" prop="toolType" v-if="columns[5].visible">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.tool_type" :value="scope.row.toolType"/>
        </template>
      </el-table-column>
      <el-table-column label="规格型号" align="center" prop="toolModel" v-if="columns[6].visible" />
      <el-table-column label="生产厂家" align="center" prop="manufacturer" v-if="columns[7].visible" />
      <el-table-column label="生产日期" align="center" prop="productionDate" width="180" v-if="columns[8].visible">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.productionDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="购置日期" align="center" prop="purchaseDate" width="180" v-if="columns[9].visible">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.purchaseDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="工器具状态" align="center" prop="toolStatus" v-if="columns[10].visible">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.tool_status" :value="scope.row.toolStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="完好性确认" align="center" prop="integrityCheck" v-if="columns[11].visible">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.integrity_check" :value="scope.row.integrityCheck"/>
        </template>
      </el-table-column>
      <el-table-column label="安全标准符合性" align="center" prop="securityStandards" v-if="columns[12].visible">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.tool_compliance" :value="scope.row.securityStandards"/>
        </template>
      </el-table-column>
      <el-table-column label="作业安全符合性" align="center" prop="jobSafety" v-if="columns[13].visible">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.tool_compliance" :value="scope.row.jobSafety"/>
        </template>
      </el-table-column>
      <el-table-column label="工器具照片" align="center" prop="photo" width="100" v-if="columns[14].visible">
        <template slot-scope="scope">
          <image-preview :src="scope.row.photo" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remarks" v-if="columns[15].visible" />
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
            v-hasPermi="['prepare:inventory:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['prepare:inventory:remove']"
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

    <!-- 添加或修改工器具检查对话框 -->
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
        <el-form-item label="工器具名称" prop="toolName">
          <el-input v-model="form.toolName" placeholder="请输入工器具名称" />
        </el-form-item>
        <el-form-item label="工器具类型" prop="toolType">
          <el-select v-model="form.toolType" placeholder="请选择工器具类型">
            <el-option
              v-for="dict in dict.type.tool_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="规格型号" prop="toolModel">
          <el-input v-model="form.toolModel" placeholder="请输入规格型号" />
        </el-form-item>
        <el-form-item label="生产厂家" prop="manufacturer">
          <el-input v-model="form.manufacturer" placeholder="请输入生产厂家" />
        </el-form-item>
        <el-form-item label="生产日期" prop="productionDate">
          <el-date-picker clearable
            v-model="form.productionDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择生产日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="购置日期" prop="purchaseDate">
          <el-date-picker clearable
            v-model="form.purchaseDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择购置日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="工器具状态" prop="toolStatus">
          <el-select v-model="form.toolStatus" placeholder="请选择工器具状态">
            <el-option
              v-for="dict in dict.type.tool_status"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="完好性确认" prop="integrityCheck">
          <el-select v-model="form.integrityCheck" placeholder="请选择完好性确认">
            <el-option
              v-for="dict in dict.type.integrity_check"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="安全标准符合性" prop="securityStandards">
          <el-select v-model="form.securityStandards" placeholder="请选择安全标准符合性">
            <el-option
              v-for="dict in dict.type.tool_compliance"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="作业安全符合性" prop="jobSafety">
          <el-select v-model="form.jobSafety" placeholder="请选择作业安全符合性">
            <el-option
              v-for="dict in dict.type.tool_compliance"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="工器具照片" prop="photo">
          <image-upload v-model="form.photo"/>
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
import { listInventory, getInventory, delInventory, addInventory, updateInventory } from "@/api/prepare/inventory";
import { listChoice } from "@/api/choice/choice";

export default {
  name: "Inventory",
  dicts: ['tool_status', 'tool_type', 'tool_compliance', 'integrity_check', 'process_status'],
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
      // 工器具检查表格数据
      inventoryList: [],
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
        toolName: null,
        toolType: null,
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
        { key: 1, label: `工器具编号`, visible: true },
        { key: 2, label: `项目名称`, visible: true },
        { key: 3, label: `承包商`, visible: true },
        { key: 4, label: `工器具名称`, visible: true },
        { key: 5, label: `工器具类型`, visible: true },
        { key: 6, label: `规格型号`, visible: true },
        { key: 7, label: `生产厂家`, visible: true },
        { key: 8, label: `生产日期`, visible: true },
        { key: 9, label: `购置日期`, visible: true },
        { key: 10, label: `工器具状态`, visible: true },
        { key: 11, label: `完好性确认`, visible: true },
        { key: 12, label: `安全标准符合性`, visible: true },
        { key: 13, label: `作业安全符合性`, visible: true },
        { key: 14, label: `工器具照片`, visible: true },
        { key: 15, label: `备注`, visible: true },
        { key: 16, label: `创建时间`, visible: true },
        { key: 17, label: `操作`, visible: true }
      ],
      // 表单校验
      rules: {
        projectName: [
          { required: true, message: "项目名称不能为空", trigger: "blur" }
        ],
        contractorName: [
          { required: true, message: "承包商不能为空", trigger: "change" }
        ],
        toolName: [
          { required: true, message: "工器具名称不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询工器具检查列表 */
    getList() {
      this.loading = true;
      listInventory(this.queryParams).then(response => {
        this.inventoryList = response.rows;
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
        toolId: null,
        toolCode: null,
        choiceId: null,
        projectName: null,
        contractorName: null,
        toolName: null,
        toolType: null,
        toolModel: null,
        manufacturer: null,
        productionDate: null,
        purchaseDate: null,
        toolStatus: null,
        integrityCheck: null,
        securityStandards: null,
        jobSafety: null,
        photo: null,
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
      this.ids = selection.map(item => item.toolId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加工器具检查";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const toolId = row.toolId || this.ids
      getInventory(toolId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改工器具检查";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.toolId != null) {
            updateInventory(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addInventory(this.form).then(response => {
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
      const toolIds = row.toolId || this.ids;
      this.$modal.confirm('是否确认删除工器具检查编号为"' + toolIds + '"的数据项？').then(function() {
        return delInventory(toolIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('prepare/inventory/export', {
        ...this.queryParams
      }, `inventory_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
