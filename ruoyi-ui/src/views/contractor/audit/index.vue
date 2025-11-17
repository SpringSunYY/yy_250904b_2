<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="承包商名称" prop="companyName">
        <el-input
          v-model="queryParams.companyName"
          placeholder="请输入承包商名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <!--      <el-form-item label="流程状态" prop="processStatus">-->
      <!--        <el-select v-model="queryParams.processStatus" placeholder="请选择流程状态" clearable>-->
      <!--          <el-option-->
      <!--            v-for="dict in dict.type.process_status"-->
      <!--            :key="dict.value"-->
      <!--            :label="dict.label"-->
      <!--            :value="dict.value"-->
      <!--          />-->
      <!--        </el-select>-->
      <!--      </el-form-item>-->
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
          v-hasPermi="['contractor:audit:add']"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['contractor:audit:edit']"
        >修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['contractor:audit:remove']"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['contractor:audit:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>


    <el-table v-loading="loading" :data="auditList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="主键id" align="center" v-if="columns[0].visible" prop="auditId"/>
      <el-table-column label="审核编号" :show-overflow-tooltip="true" align="center" v-if="columns[1].visible"
                       prop="auditNumber"/>
      <el-table-column label="承包商名称" :show-overflow-tooltip="true" align="center" v-if="columns[2].visible"
                       prop="companyName"/>
      <el-table-column label="公司规模" align="center" v-if="columns[3].visible" prop="companySize">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.contractor_company_size" :value="scope.row.companySize"/>
        </template>
      </el-table-column>
      <el-table-column label="统一社会信用代码" :show-overflow-tooltip="true" align="center" v-if="columns[4].visible"
                       prop="companyShortName"/>
      <el-table-column label="注册资本(万元)" :show-overflow-tooltip="true" align="center" v-if="columns[5].visible"
                       prop="registeredCapital"/>
      <el-table-column label="法定代表人" :show-overflow-tooltip="true" align="center" v-if="columns[6].visible"
                       prop="legalRepresentative"/>
      <el-table-column label="经营范围" :show-overflow-tooltip="true" align="center" v-if="columns[7].visible"
                       prop="businessScope"/>
      <el-table-column label="经营地址" :show-overflow-tooltip="true" align="center" v-if="columns[8].visible"
                       prop="businessAddress"/>
      <el-table-column label="成立日期" align="center" v-if="columns[9].visible" prop="establishmentDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.establishmentDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="邮箱" :show-overflow-tooltip="true" align="center" v-if="columns[10].visible"
                       prop="email"/>
      <el-table-column label="审查类型" align="center" v-if="columns[11].visible" prop="auditType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.project_audit_type" :value="scope.row.auditType"/>
        </template>
      </el-table-column>
      <el-table-column label="申请日期" align="center" v-if="columns[12].visible" prop="applyDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.applyDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="审核结果" :show-overflow-tooltip="true" align="center" v-if="columns[13].visible"
                       prop="auditResult"/>
      <el-table-column label="备注" :show-overflow-tooltip="true" align="center" v-if="columns[14].visible"
                       prop="remark"/>
      <el-table-column label="创建时间" align="center" v-if="columns[15].visible" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="申请人ID" :show-overflow-tooltip="true" align="center" v-if="columns[16].visible"
                       prop="applyUserId"/>
      <el-table-column label="申请人" :show-overflow-tooltip="true" align="center" v-if="columns[17].visible"
                       prop="applyUserName"/>
      <el-table-column label="任务ID" :show-overflow-tooltip="true" align="center" v-if="columns[18].visible"
                       prop="taskId"/>
      <el-table-column label="流程实例ID" :show-overflow-tooltip="true" align="center" v-if="columns[19].visible"
                       prop="processInstanceId"/>
      <el-table-column label="流程自定义ID" :show-overflow-tooltip="true" align="center" v-if="columns[20].visible"
                       prop="deployId"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" fixed="right">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['contractor:audit:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleProcess(scope.row)"
            v-hasPermi="['contractor:audit:list']"
          >查看流程
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['contractor:audit:remove']"
          >删除
          </el-button>
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

    <!-- 添加或修改承包商资格审查对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="承包商名称" prop="companyName">
          <el-input v-model="form.companyName" placeholder="请输入承包商名称"/>
        </el-form-item>
        <el-form-item label="公司规模" prop="companySize">
          <el-select v-model="form.companySize" placeholder="请选择公司规模">
            <el-option
              v-for="dict in dict.type.contractor_company_size"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="统一社会信用代码" prop="companyShortName">
          <el-input v-model="form.companyShortName" placeholder="请输入统一社会信用代码"/>
        </el-form-item>
        <el-form-item label="注册资本(万元)" prop="registeredCapital">
          <el-input v-model="form.registeredCapital" placeholder="请输入注册资本(万元)"/>
        </el-form-item>
        <el-form-item label="法定代表人" prop="legalRepresentative">
          <el-input v-model="form.legalRepresentative" placeholder="请输入法定代表人"/>
        </el-form-item>
        <el-form-item label="经营范围" prop="businessScope">
          <el-input v-model="form.businessScope" placeholder="请输入经营范围"/>
        </el-form-item>
        <el-form-item label="经营地址" prop="businessAddress">
          <el-input v-model="form.businessAddress" placeholder="请输入经营地址"/>
        </el-form-item>
        <el-form-item label="成立日期" prop="establishmentDate">
          <el-date-picker clearable
                          v-model="form.establishmentDate"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择成立日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="form.email" placeholder="请输入邮箱"/>
        </el-form-item>
        <el-form-item label="申请日期" prop="applyDate">
          <el-date-picker clearable
                          v-model="form.applyDate"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择申请日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" placeholder="请输入备注"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <TaskDetail :visible.sync="dialogVisible"
                :task-id="taskId"
                :proc-ins-id="procInsId"
                :deploy-id="deployId"
    />
  </div>
</template>

<script>
import {listAudit, getAudit, delAudit, addAudit, updateAudit} from "@/api/contractor/audit";
import TaskDetail from '@/components/TaskDetail/index.vue'

export default {
  components: {TaskDetail},
  name: "Audit",
  dicts: ['process_status', 'contractor_company_size', 'project_audit_type'],
  data() {
    return {
      dialogVisible: false,
      deployId: '',
      procInsId: '',
      taskId: '',
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
      // 承包商资格审查表格数据
      auditList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      //表格展示列
      columns: [
        {key: 0, label: '主键id', visible: true},
        {key: 1, label: '审核编号', visible: true},
        {key: 2, label: '承包商名称', visible: true},
        {key: 3, label: '公司规模', visible: true},
        {key: 4, label: '统一社会信用代码', visible: true},
        {key: 5, label: '注册资本(万元)', visible: true},
        {key: 6, label: '法定代表人', visible: true},
        {key: 7, label: '经营范围', visible: true},
        {key: 8, label: '经营地址', visible: true},
        {key: 9, label: '成立日期', visible: true},
        {key: 10, label: '邮箱', visible: true},
        {key: 11, label: '审查类型', visible: true},
        {key: 12, label: '申请日期', visible: true},
        {key: 13, label: '审核结果', visible: true},
        {key: 14, label: '备注', visible: true},
        {key: 15, label: '创建时间', visible: true},
        {key: 16, label: '申请人ID', visible: false},
        {key: 17, label: '申请人', visible: false},
        {key: 18, label: '任务ID', visible: false},
        {key: 19, label: '流程实例ID', visible: false},
        {key: 20, label: '流程自定义ID', visible: false},
      ],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        companyName: null,
        processStatus: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {}
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查看流程**/
    handleProcess(row) {
      if (!row.processInstanceId || !row.deployId || !row.taskId) {
        this.$message.warning('没有流程！')
        return
      }
      this.deployId = row.deployId
      this.procInsId = row.processInstanceId
      this.taskId = row.taskId
      this.dialogVisible = true
    },
    /** 查询承包商资格审查列表 */
    getList() {
      this.loading = true;
      listAudit(this.queryParams).then(response => {
        this.auditList = response.rows;
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
        auditId: null,
        auditNumber: null,
        companyName: null,
        companySize: null,
        companyShortName: null,
        registeredCapital: null,
        legalRepresentative: null,
        businessScope: null,
        businessAddress: null,
        establishmentDate: null,
        email: null,
        applyDate: null,
        processInstanceId: null,
        processStatus: null,
        currentNode: null,
        currentHandler: null,
        auditResult: null,
        remark: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null
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
      this.ids = selection.map(item => item.auditId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加承包商资格审查";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const auditId = row.auditId || this.ids
      getAudit(auditId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改承包商资格审查";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.auditId != null) {
            updateAudit(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addAudit(this.form).then(response => {
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
      const auditIds = row.auditId || this.ids;
      this.$modal.confirm('是否确认删除承包商资格审查编号为"' + auditIds + '"的数据项？').then(function () {
        return delAudit(auditIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('contractor/audit/export', {
        ...this.queryParams
      }, `audit_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
