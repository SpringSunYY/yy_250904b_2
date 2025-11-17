<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="特殊作业类型" prop="specialWorkType">
        <el-select v-model="queryParams.specialWorkType" placeholder="请选择特殊作业类型" clearable>
          <el-option
            v-for="dict in dict.type.special_work_type"
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
          v-hasPermi="['process:permit:add']"
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
          v-hasPermi="['process:permit:edit']"
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
          v-hasPermi="['process:permit:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['process:permit:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="permitList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" v-if="columns[0].visible" />
      <el-table-column label="序号" align="center" prop="permitId" v-if="columns[1].visible" />
      <el-table-column label="关联项目日志" align="center" prop="logsId" v-if="columns[2].visible">
        <template slot-scope="scope">
          <span v-for="log in projectLogsList" :key="log.logsId">
            <span v-if="log.logsId === scope.row.logsId">{{ log.logsCode }}</span>
          </span>
        </template>
      </el-table-column>
      <el-table-column label="特殊作业内容" align="center" prop="specialWork" v-if="columns[3].visible" />
      <el-table-column label="作业地点" align="center" prop="specialWorkLocation" v-if="columns[4].visible" />
      <el-table-column label="特殊作业类型" align="center" prop="specialWorkType" v-if="columns[5].visible">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.special_work_type" :value="scope.row.specialWorkType"/>
        </template>
      </el-table-column>
      <el-table-column label="安全作业票编号" align="center" prop="safetyWorkPermitNo" v-if="columns[6].visible" />
      <el-table-column label="关联作业" align="center" prop="relatedWork" v-if="columns[7].visible">
        <template slot-scope="scope">
          <span v-if="scope.row.relatedWork && ((Array.isArray(scope.row.relatedWork) && scope.row.relatedWork.length > 0) || (!Array.isArray(scope.row.relatedWork) && scope.row.relatedWork.trim() !== ''))">
            <dict-tag v-for="work in (Array.isArray(scope.row.relatedWork) ? scope.row.relatedWork : scope.row.relatedWork.split(','))"
                      :key="work"
                      :options="dict.type.special_work_type"
                      :value="work" />
          </span>
          <span v-else>-</span>
        </template>
      </el-table-column>
      <el-table-column label="作业时段" align="center" prop="workPeriod" v-if="columns[8].visible" />
      <el-table-column label="作业负责人" align="center" prop="workTeamLeader" v-if="columns[9].visible" />
      <el-table-column label="现场监护人" align="center" prop="siteGuardian" v-if="columns[10].visible" />
      <el-table-column label="作业票状态" align="center" prop="dailyPermitStatus" v-if="columns[11].visible">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.daily_permit_status" :value="scope.row.dailyPermitStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="附件" align="center" prop="attachment" v-if="columns[12].visible">
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
      <el-table-column label="备注" align="center" prop="remark" v-if="columns[13].visible" />
      <el-table-column label="创建时间" align="center" prop="createTime" width="180" v-if="columns[14].visible">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" v-if="columns[15].visible">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['process:permit:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['process:permit:remove']"
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

    <!-- 添加或修改特殊作业管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="关联项目日志" prop="logsId">
          <el-select
            v-model="form.logsId"
            placeholder="请选择关联项目日志"
            clearable
            filterable
            @focus="loadProjectLogs"
          >
            <el-option
              v-for="log in projectLogsList"
              :key="log.logsId"
              :label="log.logsCode + ' - ' + log.projectName"
              :value="log.logsId">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="特殊作业内容" prop="specialWork">
          <el-input v-model="form.specialWork" placeholder="请输入特殊作业内容" />
        </el-form-item>
        <el-form-item label="作业地点" prop="specialWorkLocation">
          <el-input v-model="form.specialWorkLocation" placeholder="请输入作业地点" />
        </el-form-item>
        <el-form-item label="特殊作业类型" prop="specialWorkType">
          <el-select v-model="form.specialWorkType" placeholder="请选择特殊作业类型">
            <el-option
              v-for="dict in dict.type.special_work_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="安全作业票编号" prop="safetyWorkPermitNo">
          <el-input v-model="form.safetyWorkPermitNo" placeholder="请输入安全作业票编号" />
        </el-form-item>
        <el-form-item label="关联作业" prop="relatedWork">
          <el-select v-model="form.relatedWork" placeholder="请选择关联作业" multiple clearable>
            <el-option
              v-for="work in relatedWorkList"
              :key="work.value"
              :label="work.label"
              :value="work.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="作业时段" prop="workPeriod">
          <el-time-picker
            v-model="form.workStartTime"
            format="HH:mm"
            value-format="HH:mm"
            placeholder="开始时间">
          </el-time-picker>
          <span style="margin: 0 10px;">-</span>
          <el-time-picker
            v-model="form.workEndTime"
            format="HH:mm"
            value-format="HH:mm"
            placeholder="结束时间">
          </el-time-picker>
        </el-form-item>
        <el-form-item label="作业负责人" prop="workTeamLeader">
          <el-select
            v-model="form.workTeamLeader"
            placeholder="请选择作业负责人"
            clearable
            filterable
            @focus="loadUsers">
            <el-option
              v-for="user in usersList"
              :key="user.userId"
              :label="user.nickName + '(' + user.userName + ')'"
              :value="user.nickName">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="现场监护人" prop="siteGuardian">
          <el-select
            v-model="form.siteGuardian"
            placeholder="请选择现场监护人"
            clearable
            filterable
            @focus="loadUsers">
            <el-option
              v-for="user in usersList"
              :key="user.userId"
              :label="user.nickName + '(' + user.userName + ')'"
              :value="user.nickName">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="作业票状态" prop="dailyPermitStatus">
          <el-select v-model="form.dailyPermitStatus" placeholder="请选择作业票状态">
            <el-option
              v-for="dict in dict.type.daily_permit_status"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="附件" prop="attachment">
          <file-upload v-model="form.attachment"/>
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
import { listPermit, getPermit, delPermit, addPermit, updatePermit } from "@/api/process/permit";
import { listLogs } from "@/api/process/logs";
import { listUser } from "@/api/system/user";

export default {
  name: "Permit",
  dicts: ['special_work_type', 'daily_permit_status'],
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
      // 特殊作业管理表格数据
      permitList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        specialWorkType: null,
      },
      // 表单参数
      form: {},
      // 表格列显隐
      columns: [
        { key: 0, visible: true, label: "选择列" },
        { key: 1, visible: true, label: "序号" },
        { key: 2, visible: true, label: "关联项目日志" },
        { key: 3, visible: true, label: "特殊作业内容" },
        { key: 4, visible: true, label: "作业地点" },
        { key: 5, visible: true, label: "特殊作业类型" },
        { key: 6, visible: true, label: "安全作业票编号" },
        { key: 7, visible: true, label: "关联作业" },
        { key: 8, visible: true, label: "作业时段" },
        { key: 9, visible: true, label: "作业负责人" },
        { key: 10, visible: true, label: "现场监护人" },
        { key: 11, visible: true, label: "作业票状态" },
        { key: 12, visible: true, label: "附件" },
        { key: 13, visible: true, label: "备注" },
        { key: 14, visible: true, label: "创建时间" },
        { key: 15, visible: true, label: "操作" }
      ],
      // 表单校验
      rules: {
      },
      // 项目日志列表
      projectLogsList: [],
      // 关联作业列表
      relatedWorkList: [
        { value: '1', label: '动火作业' },
        { value: '2', label: '受限空间作业' },
        { value: '3', label: '盲板抽堵作业' },
        { value: '4', label: '高处作业' },
        { value: '5', label: '吊装作业' },
        { value: '6', label: '临时用电作业' },
        { value: '7', label: '动土作业' },
        { value: '8', label: '断路作业' }
      ],
      // 用户列表
      usersList: []
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询特殊作业管理列表 */
    getList() {
      this.loading = true;
      listPermit(this.queryParams).then(response => {
        // 处理关联作业数据，确保它可以正确显示
        response.rows.forEach(row => {
          if (row.relatedWork && typeof row.relatedWork === 'string') {
            // 如果是逗号分隔的字符串，拆分为数组
            if (row.relatedWork.includes(',')) {
              row.relatedWork = row.relatedWork.split(',').filter(item => item.trim() !== '');
            } else if (row.relatedWork.trim() !== '') {
              // 单个非空值转为数组格式
              row.relatedWork = [row.relatedWork.trim()];
            } else {
              // 空字符串处理
              row.relatedWork = [];
            }
          } else if (row.relatedWork && Array.isArray(row.relatedWork)) {
            // 已经是数组格式，确保过滤掉空值
            row.relatedWork = row.relatedWork.filter(item => item && item.trim() !== '');
          } else {
            // 其他情况（null、undefined等）处理为空数组
            row.relatedWork = [];
          }
        });
        this.permitList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 加载项目日志列表
    loadProjectLogs() {
      listLogs({}).then(response => {
        this.projectLogsList = response.rows;
      });
    },
    // 加载用户列表
    loadUsers() {
      listUser({}).then(response => {
        this.usersList = response.rows;
      });
    },

    // 表单重置
    reset() {
      this.form = {
        permitId: null,
        logsId: null,
        specialWork: null,
        specialWorkLocation: null,
        specialWorkType: null,
        safetyWorkPermitNo: null,
        relatedWork: [],
        workStartTime: null,
        workEndTime: null,
        workPeriod: null,
        workTeamLeader: null,
        siteGuardian: null,
        dailyPermitStatus: null,
        attachment: null,
        remark: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null
      };
      this.projectLogsList = [];
      this.usersList = [];
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
      this.ids = selection.map(item => item.permitId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加特殊作业管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const permitId = row.permitId || this.ids
      getPermit(permitId).then(response => {
        this.form = response.data;
        // 解析作业时段字符串为开始时间和结束时间
        if (this.form.workPeriod) {
          const timeParts = this.form.workPeriod.split('-');
          if (timeParts.length === 2) {
            this.form.workStartTime = timeParts[0];
            this.form.workEndTime = timeParts[1];
          }
        }
        // 处理关联作业回显
        if (this.form.relatedWork && typeof this.form.relatedWork === 'string') {
          if (this.form.relatedWork.includes(',')) {
            // 逗号分隔的字符串转为数组
            this.form.relatedWork = this.form.relatedWork.split(',').filter(item => item.trim() !== '');
          } else if (this.form.relatedWork.trim() !== '') {
            // 单个非空值转为数组
            this.form.relatedWork = [this.form.relatedWork.trim()];
          } else {
            this.form.relatedWork = [];
          }
        } else if (this.form.relatedWork && Array.isArray(this.form.relatedWork)) {
          // 已经是数组，确保过滤掉空值
          this.form.relatedWork = this.form.relatedWork.filter(item => item && item.trim() !== '');
        } else {
          this.form.relatedWork = [];
        }
        this.open = true;
        this.title = "修改特殊作业管理";
        // 加载项目日志列表
        this.loadProjectLogs();
        // 加载用户列表
        this.loadUsers();
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          // 处理作业时段，将开始时间和结束时间合并为一个字符串
          if (this.form.workStartTime && this.form.workEndTime) {
            this.form.workPeriod = this.form.workStartTime + '-' + this.form.workEndTime;
          } else {
            this.form.workPeriod = '';
          }

          // 处理关联作业，将数组转换为逗号分隔的字符串
          if (this.form.relatedWork && Array.isArray(this.form.relatedWork)) {
            this.form.relatedWork = this.form.relatedWork.join(',');
          } else if (!this.form.relatedWork) {
            this.form.relatedWork = '';
          }

          if (this.form.permitId != null) {
            updatePermit(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addPermit(this.form).then(response => {
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
      const permitIds = row.permitId || this.ids;
      this.$modal.confirm('是否确认删除特殊作业管理编号为"' + permitIds + '"的数据项？').then(function() {
        return delPermit(permitIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('process/permit/export', {
        ...this.queryParams
      }, `permit_${new Date().getTime()}.xlsx`)
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
