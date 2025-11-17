<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="培训类型" prop="trainingType">
        <el-select v-model="queryParams.trainingType" placeholder="请选择培训类型" clearable>
          <el-option
            v-for="dict in dict.type.contractor_training_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
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
          v-hasPermi="['training:records:add']"
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
          v-hasPermi="['training:records:edit']"
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
          v-hasPermi="['training:records:remove']"
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
          v-hasPermi="['training:records:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="recordsList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="序号" align="center" prop="id" v-if="columns[0].visible"/>
      <el-table-column label="培训编号" align="center" prop="trainingCode" v-if="columns[1].visible"/>
      <el-table-column label="项目名称" align="center" prop="projectName" v-if="columns[2].visible"/>
      <el-table-column label="承包商" align="center" prop="contractorName" v-if="columns[3].visible"/>
      <el-table-column label="培训名称" align="center" prop="trainingName" v-if="columns[4].visible"/>
      <el-table-column label="培训类型" align="center" prop="trainingType" v-if="columns[5].visible">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.contractor_training_type" :value="scope.row.trainingType"/>
        </template>
      </el-table-column>
      <el-table-column label="培训日期" align="center" prop="trainingDate" width="180" v-if="columns[6].visible">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.trainingDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="开始时间" align="center" prop="startTime" width="180" v-if="columns[7].visible">
        <template slot-scope="scope">
          <span>{{ formatTime(scope.row.startTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="结束时间" align="center" prop="endTime" width="180" v-if="columns[8].visible">
        <template slot-scope="scope">
          <span>{{ formatTime(scope.row.endTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="培训学时" align="center" prop="trainingHours" v-if="columns[9].visible"/>
      <el-table-column label="培训内容" align="center" prop="trainingContent" v-if="columns[10].visible">
        <template slot-scope="scope">
          <span>{{ formatTrainingContent(scope.row.trainingContent) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="培训部门" align="center" prop="trainingDepartment" v-if="columns[11].visible"/>
      <el-table-column label="培训讲师" align="center" prop="trainer" v-if="columns[12].visible"/>
      <el-table-column label="培训地点" align="center" prop="trainingLocation" v-if="columns[13].visible"/>
      <el-table-column label="计划参训人数" align="center" prop="plannedAttendees" v-if="columns[14].visible"/>
      <el-table-column label="实际参训人数" align="center" prop="actualAttendees" v-if="columns[15].visible"/>
      <el-table-column label="培训状态" align="center" prop="trainingStatus" v-if="columns[16].visible">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.contractor_training_status" :value="scope.row.trainingStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="培训照片" align="center" prop="trainingPictures" width="100" v-if="columns[17].visible">
        <template slot-scope="scope">
          <image-preview :src="scope.row.trainingPictures" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="培训合格证明" align="center" prop="qualificationCertificate" v-if="columns[18].visible">
        <template slot-scope="scope">
          <image-preview :src="scope.row.qualificationCertificate" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remarks" v-if="columns[19].visible"/>
      <el-table-column label="创建时间" align="center" prop="createdAt" width="180" v-if="columns[20].visible">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createdAt, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" v-if="columns[21].visible">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['training:records:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['training:records:remove']"
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

    <!-- 添加或修改培训记录对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="项目名称" prop="projectName">
          <el-input v-model="form.projectName" placeholder="请输入项目名称">
            <el-button slot="append" icon="el-icon-search" @click="handleSelectProject"></el-button>
          </el-input>
        </el-form-item>
        <el-form-item label="承包商" prop="contractorName">
          <el-input v-model="form.contractorName" placeholder="请输入承包商"/>
        </el-form-item>
        <el-form-item label="培训名称" prop="trainingName">
          <el-input v-model="form.trainingName" placeholder="请输入培训名称"/>
        </el-form-item>
        <el-form-item label="培训类型" prop="trainingType">
          <el-select v-model="form.trainingType" placeholder="请选择培训类型">
            <el-option
              v-for="dict in dict.type.contractor_training_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="培训日期" prop="trainingDate">
          <el-date-picker clearable
                          v-model="form.trainingDate"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择培训日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="开始时间" prop="startTime">
          <el-time-picker clearable
                          v-model="form.startTime"
                          value-format="HH:mm"
                          format="HH:mm"
                          placeholder="请选择开始时间"
                          @change="calculateTrainingHours">
          </el-time-picker>
        </el-form-item>
        <el-form-item label="结束时间" prop="endTime">
          <el-time-picker clearable
                          v-model="form.endTime"
                          value-format="HH:mm"
                          format="HH:mm"
                          placeholder="请选择结束时间"
                          @change="calculateTrainingHours">
          </el-time-picker>
        </el-form-item>
        <el-form-item label="培训学时" prop="trainingHours">
          <el-input v-model="form.trainingHours" placeholder="根据开始和结束时间自动计算" readonly/>
        </el-form-item>
        <el-form-item label="培训内容">
          <editor v-model="form.trainingContent" :min-height="192"/>
        </el-form-item>
        <el-form-item label="培训部门" prop="trainingDepartment">
          <el-cascader
            v-model="form.trainingDepartment"
            :options="deptOptions"
            :props="{ checkStrictly: true, value: 'id', label: 'label', children: 'children' }"
            placeholder="请选择培训部门"
            clearable
            style="width: 100%"
            @change="handleDeptChange">
          </el-cascader>
        </el-form-item>
        <el-form-item label="培训讲师" prop="trainer">
          <el-input v-model="form.trainer" placeholder="请输入培训讲师">
            <el-button slot="append" icon="el-icon-search" @click="openUserSelect"></el-button>
          </el-input>
        </el-form-item>
        <el-form-item label="培训地点" prop="trainingLocation">
          <el-input v-model="form.trainingLocation" placeholder="请输入培训地点"/>
        </el-form-item>
        <el-form-item label="计划参训人数" prop="plannedAttendees">
          <el-input v-model="form.plannedAttendees" placeholder="请输入计划参训人数"/>
        </el-form-item>
        <el-form-item label="实际参训人数" prop="actualAttendees">
          <el-input v-model="form.actualAttendees" placeholder="请输入实际参训人数"/>
        </el-form-item>
        <el-form-item label="培训状态" prop="trainingStatus">
          <el-select v-model="form.trainingStatus" placeholder="请选择培训状态">
            <el-option
              v-for="dict in dict.type.contractor_training_status"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="培训照片" prop="trainingPictures">
          <image-upload v-model="form.trainingPictures"/>
        </el-form-item>
        <el-form-item label="培训合格证明" prop="qualificationCertificate">
          <image-upload v-model="form.qualificationCertificate"/>
        </el-form-item>
        <el-form-item label="备注" prop="remarks">
          <el-input v-model="form.remarks" type="textarea" placeholder="请输入内容"/>
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
        <el-table-column label="项目名称" align="center" prop="projectName"/>
        <el-table-column label="拟选承包商" align="center" prop="selectedContractor"/>
        <el-table-column label="流程状态" align="center" prop="processStatus">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.process_status" :value="scope.row.processStatus"/>
          </template>
        </el-table-column>
        <el-table-column label="当前节点" align="center" prop="currentNode"/>
        <el-table-column label="当前处理人" align="center" prop="currentHandler"/>
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
            >选择
            </el-button>
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
    <el-dialog :title="userDialogTitle" :visible.sync="userOpen" width="800px" append-to-body>
      <el-form :model="userQueryParams" ref="userQueryForm" size="small" :inline="true" label-width="68px">
        <el-form-item label="用户名称" prop="userName">
          <el-input
            v-model="userQueryParams.userName"
            placeholder="请输入用户名称"
            clearable
            @keyup.enter.native="handleUserQuery"
          />
        </el-form-item>
        <el-form-item label="手机号码" prop="phonenumber">
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

      <el-table v-loading="userLoading" :data="userList" @row-dblclick="selectUser" highlight-current-row>
        <el-table-column label="用户编号" align="center" prop="userId"/>
        <el-table-column label="用户名称" align="center" prop="userName"/>
        <el-table-column label="用户昵称" align="center" prop="nickName"/>
        <el-table-column label="部门" align="center" prop="dept.deptName"/>
        <el-table-column label="手机号码" align="center" prop="phonenumber"/>
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-check"
              @click="selectUser(scope.row)"
            >选择
            </el-button>
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
        <el-button @click="userOpen = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {listRecords, getRecords, delRecords, addRecords, updateRecords} from "@/api/training/records";
import {treeselect} from "@/api/system/dept";
import {listUser} from "@/api/system/user";
import {listChoice} from "@/api/choice/choice";

export default {
  name: "Records",
  dicts: ['contractor_training_status', 'contractor_training_type', 'process_status'],
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
      // 培训记录表格数据
      recordsList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 是否显示用户选择弹出层
      userOpen: false,
      // 是否显示项目选择弹出层
      projectSelectOpen: false,
      // 项目选择对话框标题
      projectDialogTitle: "选择项目",
      // 部门树选项
      deptOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        trainingType: null,
        projectName: null,
        contractorName: null
      },
      // 用户查询参数
      userQueryParams: {
        pageNum: 1,
        pageSize: 10,
        userName: undefined,
        phonenumber: undefined
      },
      // 项目查询参数
      projectQueryParams: {
        pageNum: 1,
        pageSize: 10,
        projectName: null,
        selectedContractor: null
      },
      // 用户表格数据
      userList: [],
      // 用户表格总记录数
      userTotal: 0,
      // 用户表格加载状态
      userLoading: false,
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
        {key: 0, label: `序号`, visible: true},
        {key: 1, label: `培训编号`, visible: true},
        {key: 2, label: `项目名称`, visible: true},
        {key: 3, label: `承包商`, visible: true},
        {key: 4, label: `培训名称`, visible: true},
        {key: 5, label: `培训类型`, visible: true},
        {key: 6, label: `培训日期`, visible: true},
        {key: 7, label: `开始时间`, visible: true},
        {key: 8, label: `结束时间`, visible: true},
        {key: 9, label: `培训学时`, visible: true},
        {key: 10, label: `培训内容`, visible: true},
        {key: 11, label: `培训部门`, visible: true},
        {key: 12, label: `培训讲师`, visible: true},
        {key: 13, label: `培训地点`, visible: true},
        {key: 14, label: `计划参训人数`, visible: true},
        {key: 15, label: `实际参训人数`, visible: true},
        {key: 16, label: `培训状态`, visible: true},
        {key: 17, label: `培训照片`, visible: true},
        {key: 18, label: `培训合格证明`, visible: true},
        {key: 19, label: `备注`, visible: true},
        {key: 20, label: `创建时间`, visible: true},
        {key: 21, label: `操作`, visible: true}
      ],
      // 表单校验
      rules: {
        trainingName: [
          {required: true, message: "培训名称不能为空", trigger: "blur"}
        ],
        projectName: [
          {required: true, message: "项目名称不能为空", trigger: "blur"}
        ],
        contractorName: [
          {required: true, message: "承包商不能为空", trigger: "blur"}
        ]
      }
    };
  },
  created() {
    this.getList();
    this.getDeptTree();
  },
  methods: {
    /** 查询培训记录列表 */
    getList() {
      this.loading = true;
      listRecords(this.queryParams).then(response => {
        this.recordsList = response.rows;
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
    /** 处理部门选择变化 */
    handleDeptChange(value) {
      if (value && value.length > 0) {
        // 获取最后一个选中的部门名称
        const lastDept = this.getDeptLabelById(value[value.length - 1], this.deptOptions);
        this.form.trainingDepartment = lastDept;
      } else {
        this.form.trainingDepartment = null;
      }
    },
    /** 根据部门ID获取部门名称 */
    getDeptLabelById(deptId, depts) {
      for (let i = 0; i < depts.length; i++) {
        const dept = depts[i];
        if (dept.id == deptId) {
          return dept.label;
        }
        if (dept.children && dept.children.length > 0) {
          const result = this.getDeptLabelById(deptId, dept.children);
          if (result) {
            return result;
          }
        }
      }
      return null;
    },
    /** 打开用户选择对话框 */
    openUserSelect() {
      this.getUserList();
      this.userOpen = true;
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
    /** 搜索按钮操作 */
    handleUserQuery() {
      this.userQueryParams.pageNum = 1;
      this.getUserList();
    },
    /** 重置按钮操作 */
    resetUserQuery() {
      this.userQueryParams = {
        pageNum: 1,
        pageSize: 10,
        userName: undefined,
        phonenumber: undefined
      };
      this.handleUserQuery();
    },
    /** 选择用户 */
    selectUser(row) {
      this.form.trainer = row.userName;
      this.userOpen = false;
    },
    // 格式化时间显示
    formatTime(timeValue) {
      if (!timeValue) return '';

      // 如果是字符串格式 (HH:mm)
      if (typeof timeValue === 'string' && timeValue.includes(':')) {
        return timeValue;
      }

      // 如果是Date对象
      if (timeValue instanceof Date) {
        const hours = timeValue.getHours().toString().padStart(2, '0');
        const minutes = timeValue.getMinutes().toString().padStart(2, '0');
        return `${hours}:${minutes}`;
      }

      return timeValue;
    },
    // 格式化培训内容，去除<p>标签
    formatTrainingContent(content) {
      if (!content) return '';

      // 去除开头的<p>标签
      let formattedContent = content.replace(/^<p>/, '');
      // 去除结尾的</p>标签
      formattedContent = formattedContent.replace(/<\/p>$/, '');
      // 去除可能存在的换行符
      formattedContent = formattedContent.replace(/^\s+|\s+$/g, '');

      return formattedContent;
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
        trainingCode: null,
        trainingName: null,
        projectName: null,
        contractorName: null,
        trainingType: null,
        trainingDate: null,
        startTime: null,
        endTime: null,
        trainingHours: null,
        trainingContent: null,
        trainingDepartment: null,
        trainer: null,
        trainingLocation: null,
        plannedAttendees: null,
        actualAttendees: null,
        trainingStatus: null,
        trainingPictures: null,
        qualificationCertificate: null,
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
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加培训记录";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getRecords(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改培训记录";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          // 确保提交时只传递字符串类型的trainingDepartment字段
          const submitForm = Object.assign({}, this.form);
          delete submitForm.trainingDepartmentIds;

          if (this.form.id != null) {
            updateRecords(submitForm).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addRecords(submitForm).then(response => {
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
      this.$modal.confirm('是否确认删除培训记录编号为"' + ids + '"的数据项？').then(function () {
        return delRecords(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('training/records/export', {
        ...this.queryParams
      }, `records_${new Date().getTime()}.xlsx`)
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
    /** 计算培训学时 */
    calculateTrainingHours() {
      if (this.form.startTime && this.form.endTime) {
        // 解析开始时间和结束时间
        const startTime = this.form.startTime;
        const endTime = this.form.endTime;

        // 如果是字符串格式的时间 (HH:mm)
        if (typeof startTime === 'string' && typeof endTime === 'string') {
          const startParts = startTime.split(':').map(Number);
          const endParts = endTime.split(':').map(Number);

          const startMinutes = startParts[0] * 60 + startParts[1];
          let endMinutes = endParts[0] * 60 + endParts[1];

          // 如果结束时间小于开始时间，则认为是跨天
          if (endMinutes < startMinutes) {
            endMinutes += 24 * 60;
          }

          // 计算小时数，保留两位小数
          const hours = (endMinutes - startMinutes) / 60;
          this.form.trainingHours = parseFloat(hours.toFixed(2));
        }
      } else {
        this.form.trainingHours = null;
      }
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
    }
  }
};
</script>
