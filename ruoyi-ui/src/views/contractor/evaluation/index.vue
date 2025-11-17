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
          v-hasPermi="['contractor:evaluation:add']"
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
          v-hasPermi="['contractor:evaluation:edit']"
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
          v-hasPermi="['contractor:evaluation:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['contractor:evaluation:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="evaluationList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" v-if="columns[0].visible" />
      <el-table-column label="序号" align="center" prop="id" v-if="columns[1].visible" />
      <el-table-column label="评价编号" align="center" prop="evaluationCode" v-if="columns[2].visible" />
      <el-table-column label="项目名称" align="center" prop="projectName" v-if="columns[3].visible" />
      <el-table-column label="承包商" align="center" prop="contractorName" v-if="columns[4].visible" />
      <el-table-column label="评价内容" align="center" prop="reviewContent" v-if="columns[5].visible" />
      <el-table-column label="评价时间" align="center" prop="evaluationDate" width="180" v-if="columns[6].visible">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.evaluationDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="评价部门" align="center" prop="evaluationDepartment" v-if="columns[7].visible" />
      <el-table-column label="评价人员" align="center" prop="evaluatorName" v-if="columns[8].visible" />
      <el-table-column label="得分" align="center" prop="score" v-if="columns[9].visible" />
      <el-table-column label="评价结论" align="center" prop="evaluationConclusion" v-if="columns[10].visible">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.evaluation_conclusion" :value="scope.row.evaluationConclusion"/>
        </template>
      </el-table-column>
      <el-table-column label="相关附件" align="center" prop="attachments" v-if="columns[11].visible">
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
      <el-table-column label="备注" align="center" prop="notes" v-if="columns[12].visible" />
      <el-table-column label="创建时间" align="center" prop="createdAt" width="180" v-if="columns[13].visible">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createdAt, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" v-if="columns[14].visible">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['contractor:evaluation:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['contractor:evaluation:remove']"
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

    <!-- 添加或修改安全业绩评价对话框 -->
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
        <el-form-item label="评价内容" prop="reviewContent">
          <el-input v-model="form.reviewContent" placeholder="请输入评价内容" />
        </el-form-item>
        <el-form-item label="评价时间" prop="evaluationDate">
          <el-date-picker clearable
            v-model="form.evaluationDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择评价时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="评价部门" prop="evaluationDepartment">
          <el-cascader
            v-model="form.evaluationDepartment"
            :options="deptOptions"
            :props="{ checkStrictly: true, value: 'label', label: 'label', emitPath: false }"
            placeholder="请选择评价部门"
            clearable>
          </el-cascader>
        </el-form-item>
        <el-form-item label="评价人员" prop="evaluatorName">
          <el-select
            v-model="form.evaluatorName"
            multiple
            filterable
            allow-create
            default-first-option
            placeholder="请选择或输入评价人员">
            <el-option
              v-for="user in userList"
              :key="user.userId"
              :label="user.userName"
              :value="user.userName">
            </el-option>
          </el-select>
          <el-button icon="el-icon-search" @click="openUserSelect" style="margin-left: 10px;"></el-button>
        </el-form-item>
        <el-form-item label="得分" prop="score">
          <el-input v-model="form.score" placeholder="请输入得分" />
        </el-form-item>
        <el-form-item label="评价结论" prop="evaluationConclusion">
          <el-select v-model="form.evaluationConclusion" placeholder="请选择评价结论">
            <el-option
              v-for="dict in dict.type.evaluation_conclusion"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
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
      <el-table
        v-loading="userLoading"
        :data="userList"
        @selection-change="handleUserSelectionChange"
        ref="userTable"
        highlight-current-row>
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="用户编号" align="center" prop="userId" />
        <el-table-column label="用户名称" align="center" prop="userName" />
        <el-table-column label="用户昵称" align="center" prop="nickName" />
        <el-table-column label="部门" align="center" prop="dept.deptName" />
        <el-table-column label="手机号码" align="center" prop="phonenumber" />
        <el-table-column label="状态" align="center" prop="status">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.sys_normal_disable" :value="scope.row.status"/>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-check"
              @click="handleSelectUserRow(scope.row)"
            >选择</el-button>
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
        <el-button type="primary" @click="confirmUserSelection">确 定</el-button>
        <el-button @click="userSelectOpen = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listEvaluation, getEvaluation, delEvaluation, addEvaluation, updateEvaluation } from "@/api/contractor/evaluation";
import { listChoice } from "@/api/choice/choice";
import { treeselect } from "@/api/system/dept";
import { listUser } from "@/api/system/user";

export default {
  name: "Evaluation",
  dicts: ['evaluation_conclusion', 'process_status', 'sys_normal_disable'],
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
      // 安全业绩评价表格数据
      evaluationList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 是否显示项目选择弹出层
      projectSelectOpen: false,
      // 是否显示用户选择弹出层
      userSelectOpen: false,
      // 项目选择对话框标题
      projectDialogTitle: "选择项目",
      // 用户选择对话框标题
      userDialogTitle: "选择评价人员",
      // 选中的用户
      selectedUsers: [],
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
      // 用户查询参数
      userQueryParams: {
        pageNum: 1,
        pageSize: 10,
        userName: null,
        phonenumber: null
      },
      // 项目总条数
      projectTotal: 0,
      // 用户总条数
      userTotal: 0,
      // 项目表格数据
      projectList: [],
      // 用户表格数据
      userList: [],
      // 项目加载状态
      projectLoading: false,
      // 用户加载状态
      userLoading: false,
      // 部门树选项
      deptOptions: [],
      // 表格列显隐
      columns: [
        { key: 0, visible: true, label: "选择列" },
        { key: 1, visible: true, label: "序号" },
        { key: 2, visible: true, label: "评价编号" },
        { key: 3, visible: true, label: "项目名称" },
        { key: 4, visible: true, label: "承包商" },
        { key: 5, visible: true, label: "评价内容" },
        { key: 6, visible: true, label: "评价时间" },
        { key: 7, visible: true, label: "评价部门" },
        { key: 8, visible: true, label: "评价人员" },
        { key: 9, visible: true, label: "得分" },
        { key: 10, visible: true, label: "评价结论" },
        { key: 11, visible: true, label: "相关附件" },
        { key: 12, visible: true, label: "备注" },
        { key: 13, visible: true, label: "创建时间" },
        { key: 14, visible: true, label: "操作" }
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
    this.getDeptTree();
  },
  methods: {
    /** 查询安全业绩评价列表 */
    getList() {
      this.loading = true;
      listEvaluation(this.queryParams).then(response => {
        this.evaluationList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 获取部门树
    getDeptTree() {
      treeselect().then(response => {
        this.deptOptions = response.data;
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
        id: null,
        evaluationCode: null,
        choiceId: null,
        projectName: null,
        contractorName: null,
        reviewContent: null,
        evaluationDate: null,
        evaluationDepartment: null, // 级联选择器的值可以是 null
        evaluatorName: [], // 修改为数组以支持多选
        score: null,
        evaluationConclusion: null,
        attachments: null,
        notes: null,
        createdBy: null,
        createdAt: null,
        updatedBy: null,
        updatedAt: null
      };
      this.selectedUsers = [];
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
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加安全业绩评价";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getEvaluation(id).then(response => {
        this.form = response.data;
        // 如果评价部门是字符串且包含分隔符，则只取最后一个部门
        if (this.form.evaluationDepartment && this.form.evaluationDepartment.includes('/')) {
          const deptParts = this.form.evaluationDepartment.split('/');
          this.form.evaluationDepartment = deptParts[deptParts.length - 1];
        }
        // 如果评价人员是字符串，则转换为数组
        if (this.form.evaluatorName && typeof this.form.evaluatorName === 'string') {
          this.form.evaluatorName = this.form.evaluatorName.split(',');
        } else if (!this.form.evaluatorName) {
          this.form.evaluatorName = [];
        }
        this.open = true;
        this.title = "修改安全业绩评价";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          // 处理评价部门字段，如果是数组则转换为字符串
          const submitForm = Object.assign({}, this.form);
          if (Array.isArray(submitForm.evaluationDepartment)) {
            submitForm.evaluationDepartment = submitForm.evaluationDepartment.join('/');
          }

          // 处理评价人员字段，如果是数组则转换为字符串
          if (Array.isArray(submitForm.evaluatorName)) {
            submitForm.evaluatorName = submitForm.evaluatorName.join(',');
          }

          if (this.form.id != null) {
            updateEvaluation(submitForm).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addEvaluation(submitForm).then(response => {
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
      this.$modal.confirm('是否确认删除安全业绩评价编号为"' + ids + '"的数据项？').then(function() {
        return delEvaluation(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('contractor/evaluation/export', {
        ...this.queryParams
      }, `evaluation_${new Date().getTime()}.xlsx`)
    },
    /** 打开用户选择对话框 */
    openUserSelect() {
      this.getUserList();
      this.userSelectOpen = true;
      this.$nextTick(() => {
        // 清除之前的选择
        this.$refs.userTable.clearSelection();
        // 如果表单中已有选中的用户，则在表格中选中它们
        if (this.form.evaluatorName && Array.isArray(this.form.evaluatorName)) {
          this.form.evaluatorName.forEach(userName => {
            const user = this.userList.find(u => u.userName === userName);
            if (user) {
              this.$refs.userTable.toggleRowSelection(user, true);
            }
          });
        }
      });
    },
    /** 查询用户列表 */
    getUserList() {
      this.userLoading = true;
      listUser(this.userQueryParams).then(response => {
        this.userList = response.rows;
        this.userTotal = response.total;
        this.userLoading = false;
      });
    },
    /** 用户选择改变 */
    handleUserSelectionChange(selection) {
      this.selectedUsers = selection;
    },
    /** 确认用户选择 */
    confirmUserSelection() {
      if (this.selectedUsers.length > 0) {
        // 更新表单中的评价人员字段
        this.form.evaluatorName = this.selectedUsers.map(user => user.userName);
      }
      this.userSelectOpen = false;
    },
    /** 选择用户行 */
    handleSelectUserRow(row) {
      // 切换行的选中状态
      this.$refs.userTable.toggleRowSelection(row);
    },
    /** 搜索用户 */
    handleUserQuery() {
      this.userQueryParams.pageNum = 1;
      this.getUserList();
    },
    /** 重置用户查询 */
    resetUserQuery() {
      this.userQueryParams = {
        pageNum: 1,
        pageSize: 10,
        userName: null,
        phonenumber: null
      };
      this.getUserList();
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
    }
  }
};
</script>
