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
      <el-form-item label="项目类型" prop="projectType">
        <el-select v-model="queryParams.projectType" placeholder="请选择项目类型" clearable>
          <el-option
            v-for="dict in dict.type.project_type"
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
          v-hasPermi="['contractor:project:add']"
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
          v-hasPermi="['contractor:project:edit']"
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
          v-hasPermi="['contractor:project:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['contractor:project:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="projectList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" prop="projectId" v-if="columns[0].visible" />
      <el-table-column label="项目编号" align="center" prop="projectCode" v-if="columns[1].visible" />
      <el-table-column label="项目名称" align="center" prop="projectName" v-if="columns[2].visible" />
      <el-table-column label="项目内容" align="center" prop="projectContent" v-if="columns[3].visible">
        <template slot-scope="scope">
          <!-- 使用v-html来渲染HTML内容，并通过过滤器去除<p>标签 -->
          <span v-html="removePTags(scope.row.projectContent)"></span>
        </template>
      </el-table-column>
      <el-table-column label="项目类型" align="center" prop="projectType" v-if="columns[4].visible">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.project_type" :value="scope.row.projectType"/>
        </template>
      </el-table-column>
      <el-table-column label="项目预算(万元)" align="center" prop="projectBudget" v-if="columns[5].visible" />
      <el-table-column label="项目地点" align="center" prop="projectLocation" v-if="columns[6].visible" />
      <el-table-column label="计划开始日期" align="center" prop="plannedStartDate" width="180" v-if="columns[7].visible">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.plannedStartDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="计划结束日期" align="center" prop="plannedEndDate" width="180" v-if="columns[8].visible">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.plannedEndDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="实际开始日期" align="center" prop="actualStartDate" width="180" v-if="columns[9].visible">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.actualStartDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="实际结束日期" align="center" prop="actualEndDate" width="180" v-if="columns[10].visible">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.actualEndDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="技术要求" align="center" prop="technicalRequirements" v-if="columns[11].visible">
        <template slot-scope="scope">
          <!-- 使用v-html来渲染HTML内容，并通过过滤器去除<p>标签 -->
          <span v-html="removePTags(scope.row.technicalRequirements)"></span>
        </template>
      </el-table-column>
      <el-table-column label="责任部门" align="center" prop="responsibleDepartment" v-if="columns[12].visible">
        <template slot-scope="scope">
          <!-- 只显示最后一级部门名称 -->
          <span>{{ scope.row.responsibleDepartment ? scope.row.responsibleDepartment.split('/').pop() : '' }}</span>
        </template>
      </el-table-column>
      <el-table-column label="项目负责人" align="center" prop="projectLeader" v-if="columns[13].visible" />
      <el-table-column label="项目状态" align="center" prop="projectStatus" v-if="columns[14].visible">
        <template slot-scope="scope">
          <dict-tag v-if="dict.type.project_status && dict.type.project_status.length > 0"
                    :options="dict.type.project_status"
                    :value="scope.row.projectStatus"/>
          <span v-else>{{ scope.row.projectStatus }}</span>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createdAt" width="180" v-if="columns[15].visible">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createdAt, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" v-if="columns[16].visible" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" v-if="columns[17].visible">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['contractor:project:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['contractor:project:remove']"
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

    <!-- 添加或修改发包项目信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="项目名称" prop="projectName">
          <el-input v-model="form.projectName" placeholder="请输入项目名称" />
        </el-form-item>
        <el-form-item label="项目内容">
          <editor v-model="form.projectContent" :min-height="192"/>
        </el-form-item>
        <el-form-item label="项目类型" prop="projectType">
          <el-select v-model="form.projectType" placeholder="请选择项目类型">
            <el-option
              v-for="dict in dict.type.project_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="项目预算(万元)" prop="projectBudget">
          <el-input v-model="form.projectBudget" placeholder="请输入项目预算" />
        </el-form-item>
        <el-form-item label="项目地点" prop="projectLocation">
          <el-input v-model="form.projectLocation" placeholder="请输入项目地点" />
        </el-form-item>
        <el-form-item label="计划开始日期" prop="plannedStartDate">
          <el-date-picker clearable
            v-model="form.plannedStartDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择计划开始日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="计划结束日期" prop="plannedEndDate">
          <el-date-picker clearable
            v-model="form.plannedEndDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择计划结束日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="实际开始日期" prop="actualStartDate">
          <el-date-picker clearable
            v-model="form.actualStartDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择实际开始日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="实际结束日期" prop="actualEndDate">
          <el-date-picker clearable
            v-model="form.actualEndDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择实际结束日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="技术要求">
          <editor v-model="form.technicalRequirements" :min-height="192"/>
        </el-form-item>
        <el-form-item label="责任部门" prop="responsibleDepartment">
          <el-cascader
            v-model="form.responsibleDepartment"
            :options="deptOptions"
            :props="deptProps"
            placeholder="请选择责任部门"
            clearable
            style="width: 100%"
          />
        </el-form-item>
        <el-form-item label="项目负责人" prop="projectLeader">
          <el-select v-model="form.projectLeader" placeholder="请选择项目负责人" clearable filterable>
            <el-option
              v-for="user in userOptions"
              :key="user.userId"
              :label="user.nickName"
              :value="user.nickName">
              <span style="float: left">{{ user.nickName }}</span>
              <span style="float: right; color: #8492a6; font-size: 13px">{{ user.dept.deptName }}</span>
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="项目状态" prop="projectStatus">
          <el-select v-model="form.projectStatus" placeholder="请选择项目状态">
            <el-option
              v-for="dict in dict.type.project_status"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
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
  </div>
</template>

<script>
import { listProject, getProject, delProject, addProject, updateProject } from "@/api/contractor/project";
import { treeselect } from "@/api/system/dept";
import { listUser } from "@/api/system/user";

export default {
  name: "Project",
  dicts: ['project_status', 'project_type'],
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
      // 发包项目信息表格数据
      projectList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 部门树选项
      deptOptions: [],
      // 用户选项
      userOptions: [],
      // 部门级联属性
      deptProps: {
        value: "label",
        label: "label",
        children: "children",
        expandTrigger: "hover"
      },
      // 列信息
      columns: [
        { key: 0, value: 'projectId', label: '序号', visible: true },
        { key: 1, value: 'projectCode', label: '项目编号', visible: true },
        { key: 2, value: 'projectName', label: '项目名称', visible: true },
        { key: 3, value: 'projectContent', label: '项目内容', visible: true },
        { key: 4, value: 'projectType', label: '项目类型', visible: true },
        { key: 5, value: 'projectBudget', label: '项目预算(万元)', visible: true },
        { key: 6, value: 'projectLocation', label: '项目地点', visible: true },
        { key: 7, value: 'plannedStartDate', label: '计划开始日期', visible: true },
        { key: 8, value: 'plannedEndDate', label: '计划结束日期', visible: true },
        { key: 9, value: 'actualStartDate', label: '实际开始日期', visible: true },
        { key: 10, value: 'actualEndDate', label: '实际结束日期', visible: true },
        { key: 11, value: 'technicalRequirements', label: '技术要求', visible: true },
        { key: 12, value: 'responsibleDepartment', label: '责任部门', visible: true },
        { key: 13, value: 'projectLeader', label: '项目负责人', visible: true },
        { key: 14, value: 'projectStatus', label: '项目状态', visible: true },
        { key: 15, value: 'createdAt', label: '创建时间', visible: true },
        { key: 16, value: 'remark', label: '备注', visible: true },
        { key: 17, value: 'operation', label: '操作', visible: true }
      ],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        projectName: null,
        projectType: null,
        responsibleDepartment: null,
        projectLeader: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        projectName: [
          { required: true, message: "项目名称不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
    this.getDeptTree();
    this.getUserList();
  },
  methods: {
    /** 查询发包项目信息列表 */
    getList() {
      this.loading = true;
      listProject(this.queryParams).then(response => {
        this.projectList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 查询部门下拉树 */
    getDeptTree() {
      treeselect().then(response => {
        this.deptOptions = response.data;
      });
    },
    /** 查询用户列表 */
    getUserList() {
      listUser({ pageSize: 9999 }).then(response => {
        this.userOptions = response.rows;
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
        projectId: null,
        projectCode: null,
        projectName: null,
        projectContent: null,
        projectType: null,
        projectBudget: null,
        projectLocation: null,
        plannedStartDate: null,
        plannedEndDate: null,
        actualStartDate: null,
        actualEndDate: null,
        technicalRequirements: null,
        responsibleDepartment: null,
        projectLeader: null,
        projectStatus: null,
        createdBy: null,
        createdAt: null,
        remark: null,
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
      this.ids = selection.map(item => item.projectId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加发包项目信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const projectId = row.projectId || this.ids
      getProject(projectId).then(response => {
        this.form = response.data;
        // 确保项目类型值是字符串类型，以便与字典选项匹配
        if (this.form.projectType) {
          this.form.projectType = this.form.projectType.toString();
        }
        this.open = true;
        this.title = "修改发包项目信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          // 处理级联选择器的值，将其转换为字符串
          if (Array.isArray(this.form.responsibleDepartment)) {
            this.form.responsibleDepartment = this.form.responsibleDepartment.join('/');
          }
          if (this.form.projectId != null) {
            updateProject(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addProject(this.form).then(response => {
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
      const projectIds = row.projectId || this.ids;
      this.$modal.confirm('是否确认删除发包项目信息编号为"' + projectIds + '"的数据项？').then(function() {
        return delProject(projectIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('contractor/project/export', {
        ...this.queryParams
      }, `project_${new Date().getTime()}.xlsx`)
    },

    /**
     * 去除HTML中的<p>标签
     */
    removePTags(htmlContent) {
      if (!htmlContent) {
        return '';
      }
      // 使用正则表达式替换<p>标签，只保留内容
      return htmlContent.replace(/<p[^>]*>(.*?)<\/p>/g, '$1');
    }
  }
};
</script>
