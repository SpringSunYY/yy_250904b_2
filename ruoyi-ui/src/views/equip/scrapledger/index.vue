<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="设备名称" prop="equipName">
        <el-input
          v-model="queryParams.equipName"
          placeholder="请输入设备名称"
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
          v-hasPermi="['equip:scrapledger:add']"
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
          v-hasPermi="['equip:scrapledger:edit']"
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
          v-hasPermi="['equip:scrapledger:remove']"
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
          v-hasPermi="['equip:scrapledger:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="scrapledgerList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="报废ID" align="center" v-if="columns[0].visible" prop="scrapId"/>
      <el-table-column label="报废编号" :show-overflow-tooltip="true" align="center" v-if="columns[1].visible"
                       prop="scrapNo"
      />
      <el-table-column label="设备ID" align="center" v-if="columns[2].visible" prop="equipmentId"/>
      <el-table-column label="设备名称" :show-overflow-tooltip="true" align="center" v-if="columns[3].visible"
                       prop="equipName"
      />
      <el-table-column label="设备位号" :show-overflow-tooltip="true" align="center" v-if="columns[4].visible"
                       prop="equCode"
      />
      <el-table-column label="报废原因" align="center" v-if="columns[5].visible" prop="scrapReason">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.equip_scrap_reason" :value="scope.row.scrapReason"/>
        </template>
      </el-table-column>
      <el-table-column label="申请时间" align="center" v-if="columns[6].visible" prop="applicationTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.applicationTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="申请人ID" align="center" v-if="columns[7].visible" prop="applicantId"/>
      <el-table-column label="申请人" :show-overflow-tooltip="true" align="center" v-if="columns[8].visible"
                       prop="applicantName"
      />
      <el-table-column label="技术鉴定意见" :show-overflow-tooltip="true" align="center" v-if="columns[9].visible"
                       prop="techAssessment"
      />
      <el-table-column label="财务审核意见" :show-overflow-tooltip="true" align="center" v-if="columns[10].visible"
                       prop="financialReview"
      />
      <el-table-column label="处理方式" align="center" v-if="columns[11].visible" prop="scrapMethod">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.equip_scrap_method" :value="scope.row.scrapMethod"/>
        </template>
      </el-table-column>
      <el-table-column label="申请部门ID" :show-overflow-tooltip="true" align="center" v-if="columns[12].visible"
                       prop="deptId"
      />
      <el-table-column label="申请部门" :show-overflow-tooltip="true" align="center" v-if="columns[13].visible"
                       prop="deptName"
      />
      <el-table-column label="申请人ID" :show-overflow-tooltip="true" align="center" v-if="columns[14].visible"
                       prop="applyUserId"
      />
      <el-table-column label="申请人" :show-overflow-tooltip="true" align="center" v-if="columns[15].visible"
                       prop="applyUserName"
      />
      <el-table-column label="任务ID" :show-overflow-tooltip="true" align="center" v-if="columns[16].visible"
                       prop="taskId"
      />
      <el-table-column label="流程实例ID" align="center" v-if="columns[17].visible" prop="processInstanceId">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_process_category" :value="scope.row.processInstanceId"/>
        </template>
      </el-table-column>
      <el-table-column label="流程自定义ID" :show-overflow-tooltip="true" align="center" v-if="columns[18].visible"
                       prop="deployId"
      />
      <el-table-column label="备注" :show-overflow-tooltip="true" align="center" v-if="columns[19].visible"
                       prop="remark"
      />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" fixed="right">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['equip:scrapledger:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleProcess(scope.row)"
            v-hasPermi="['equip:scrapledger:list']"
          >查看流程
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['equip:scrapledger:remove']"
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

    <!-- 添加或修改设备报废管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="900px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="设备" prop="equipmentId">
          <el-row>
            <el-col :span="20">
              <el-select
                v-model="form.equipmentId"
                placeholder="请选择设备"
                @change="handleEquipChange"
                style="width: 100%"
              >
                <el-option
                  v-for="item in equipList"
                  :key="item.equipId"
                  :label="item.equName"
                  :value="item.equipId"
                >
                  <span style="float: left">{{ item.equName }}</span>
                  <span style="float: right; color: #8492a6; font-size: 13px">{{ item.equCode }}</span>
                </el-option>
              </el-select>
            </el-col>
            <el-col :span="4">
              <el-button type="primary" @click="openEquipSelector" style="margin-left: 10px;">选择设备</el-button>
            </el-col>
          </el-row>
        </el-form-item>
        <el-form-item label="设备位号" prop="equCode">
          <el-input v-model="form.equCode" placeholder="设备位号" disabled/>
        </el-form-item>
        <el-form-item label="报废原因" prop="scrapReason">
          <el-select v-model="form.scrapReason" placeholder="请选择报废原因">
            <el-option
              v-for="dict in dict.type.equip_scrap_reason"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="申请时间" prop="applicationTime">
          <el-date-picker clearable
                          v-model="form.applicationTime"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择申请时间"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="申请人" prop="applicantId">
          <el-select v-model="form.applicantId" placeholder="请选择申请人" filterable remote :remote-method="queryUsers"
                     :loading="userLoading"
          >
            <el-option
              v-for="user in userList"
              :key="user.userId"
              :label="user.nickName"
              :value="user.userId"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="技术鉴定" prop="techAssessment">
          <el-input v-model="form.techAssessment" type="textarea" placeholder="请输入内容"/>
        </el-form-item>
        <el-form-item label="财务审核" prop="financialReview">
          <el-input v-model="form.financialReview" type="textarea" placeholder="请输入内容"/>
        </el-form-item>
        <el-form-item label="处理方式" prop="scrapMethod">
          <el-select v-model="form.scrapMethod" placeholder="请选择处理方式">
            <el-option
              v-for="dict in dict.type.equip_scrap_method"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 设备选择对话框 -->
    <el-dialog title="选择设备" :visible.sync="equipSelectorOpen" width="800px" append-to-body>
      <el-form :model="equipQueryParams" ref="equipQueryForm" size="small" :inline="true" label-width="68px">
        <el-form-item label="设备名称">
          <el-input
            v-model="equipQueryParams.equName"
            placeholder="请输入设备名称"
            clearable
            @keyup.enter.native="handleEquipQuery"
          />
        </el-form-item>
        <el-form-item label="设备位号">
          <el-input
            v-model="equipQueryParams.equCode"
            placeholder="请输入设备位号"
            clearable
            @keyup.enter.native="handleEquipQuery"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleEquipQuery">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetEquipQuery">重置</el-button>
        </el-form-item>
      </el-form>

      <el-table
        v-loading="equipLoading"
        :data="equipListSelector"
        @row-dblclick="handleSelectEquip"
        max-height="400"
      >
        <el-table-column label="设备名称" align="center" prop="equName"/>
        <el-table-column label="设备位号" align="center" prop="equCode"/>
        <el-table-column label="设备类型" align="center" prop="type">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.equip_type" :value="scope.row.type"/>
          </template>
        </el-table-column>
        <el-table-column label="管理级别" align="center" prop="equipLevels">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.equip_levels" :value="scope.row.equipLevels"/>
          </template>
        </el-table-column>
        <el-table-column label="规格型号" align="center" prop="specification"/>
        <el-table-column label="安装位置" align="center" prop="installLocation">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.equip_location" :value="scope.row.installLocation"/>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-check"
              @click="handleSelectEquip(scope.row)"
            >选择
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <pagination
        v-show="equipTotal>0"
        :total="equipTotal"
        :page.sync="equipQueryParams.pageNum"
        :limit.sync="equipQueryParams.pageSize"
        @pagination="getEquipList"
      />

      <div slot="footer" class="dialog-footer">
        <el-button @click="equipSelectorOpen = false">关 闭</el-button>
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
import {
  listScrapledger,
  getScrapledger,
  delScrapledger,
  addScrapledger,
  updateScrapledger
} from '@/api/equip/scrapledger'
import { listLedger } from '@/api/equip/ledger'
import { listUser } from '@/api/system/user'
import TaskDetail from '@/components/TaskDetail/index.vue'

export default {
  name: 'Scrapledger',
  components: { TaskDetail },
  dicts: ['equip_scrap_reason', 'equip_user', 'equip_scrap_method', 'equip_repair_sratus', 'equip_ledger', 'sys_process_category', 'equip_type', 'equip_levels', 'equip_location'],
  data() {
    return {
      //表格展示列
      columns: [
        { key: 0, label: '报废ID', visible: true },
        { key: 1, label: '报废编号', visible: true },
        { key: 2, label: '设备ID', visible: false },
        { key: 3, label: '设备名称', visible: true },
        { key: 4, label: '设备位号', visible: true },
        { key: 5, label: '报废原因', visible: true },
        { key: 6, label: '申请时间', visible: true },
        { key: 7, label: '申请人ID', visible: false },
        { key: 8, label: '申请人', visible: true },
        { key: 9, label: '技术鉴定意见', visible: true },
        { key: 10, label: '财务审核意见', visible: true },
        { key: 11, label: '处理方式', visible: true },
        { key: 12, label: '申请部门ID', visible: false },
        { key: 13, label: '申请部门', visible: false },
        { key: 14, label: '申请人ID', visible: false },
        { key: 15, label: '申请人', visible: false },
        { key: 16, label: '任务ID', visible: false },
        { key: 17, label: '流程实例ID', visible: false },
        { key: 18, label: '流程自定义ID', visible: false },
        { key: 19, label: '备注', visible: false }
      ],
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
      // 设备报废管理表格数据
      scrapledgerList: [],
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 是否显示设备选择弹出层
      equipSelectorOpen: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        equipName: null
      },
      // 设备查询参数
      equipQueryParams: {
        pageNum: 1,
        pageSize: 10,
        equName: undefined,
        equCode: undefined
      },
      // 设备列表
      equipList: [],
      // 设备选择器列表
      equipListSelector: [],
      // 设备总数
      equipTotal: 0,
      // 设备加载
      equipLoading: false,
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        equipmentId: [
          { required: true, message: '设备不能为空', trigger: 'change' }
        ]
      },
      // 用户列表
      userList: [],
      // 用户加载状态
      userLoading: false,
      // 用户查询参数
      userQueryParams: {
        pageNum: 1,
        pageSize: 100,
        userName: undefined,
        nickName: undefined
      }
    }
  },
  created() {
    this.getList()
    this.getEquipList()
    this.getUserList()
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
    /** 查询设备报废管理列表 */
    getList() {
      this.loading = true
      listScrapledger(this.queryParams).then(response => {
        this.scrapledgerList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        scrapId: null,
        scrapNo: null,
        equipmentId: null,
        equipName: null,
        equCode: null,
        scrapReason: null,
        applicationTime: null,
        applicantId: null,
        applicantName: null,
        techAssessment: null,
        financialReview: null,
        scrapMethod: null,
        deptId: null,
        deptName: null,
        applyUserId: null,
        applyUserName: null,
        taskId: null,
        processInstanceId: null,
        deployId: null,
        remark: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null
      }
      this.resetForm('form')
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm('queryForm')
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.scrapId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = '添加设备报废管理'
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const scrapId = row.scrapId || this.ids
      getScrapledger(scrapId).then(response => {
        this.form = response.data
        this.open = true
        this.title = '修改设备报废管理'
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          // 处理申请人姓名
          if (this.form.applicantId) {
            const selectedUser = this.userList.find(user => user.userId === this.form.applicantId)
            if (selectedUser) {
              this.form.applicantName = selectedUser.nickName
            }
          }

          if (this.form.scrapId != null) {
            updateScrapledger(this.form).then(response => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addScrapledger(this.form).then(response => {
              this.$modal.msgSuccess('新增成功')
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const scrapIds = row.scrapId || this.ids
      this.$modal.confirm('是否确认删除设备报废管理编号为"' + scrapIds + '"的数据项？').then(function() {
        return delScrapledger(scrapIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess('删除成功')
      }).catch(() => {
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('equip/scrapledger/export', {
        ...this.queryParams
      }, `scrapledger_${new Date().getTime()}.xlsx`)
    },
    /** 获取设备列表 */
    getEquipList() {
      this.equipLoading = true
      listLedger(this.equipQueryParams).then(response => {
        this.equipListSelector = response.rows
        this.equipTotal = response.total
        this.equipLoading = false

        // 同时更新下拉框中的设备列表
        if (!this.equipQueryParams.equName && !this.equipQueryParams.equCode) {
          this.equipList = response.rows
        }
      })
    },
    /** 设备搜索 */
    handleEquipQuery() {
      this.equipQueryParams.pageNum = 1
      this.getEquipList()
    },
    /** 重置设备搜索 */
    resetEquipQuery() {
      this.resetForm('equipQueryForm')
      this.handleEquipQuery()
    },
    /** 选择设备 */
    handleSelectEquip(row) {
      this.form.equipmentId = row.equipId
      this.form.equipName = row.equName
      this.form.equCode = row.equCode
      this.equipSelectorOpen = false
    },
    /** 打开设备选择器 */
    openEquipSelector() {
      this.equipSelectorOpen = true
      this.resetEquipQuery()
    },
    /** 设备变更 */
    handleEquipChange(val) {
      const selectedEquip = this.equipList.find(item => item.equipId === val)
      if (selectedEquip) {
        this.form.equipName = selectedEquip.equName
        this.form.equCode = selectedEquip.equCode
      }
    },
    // 获取用户列表
    getUserList() {
      this.userLoading = true
      listUser(this.userQueryParams).then(response => {
        this.userList = response.rows
        this.userLoading = false
      }).catch(() => {
        this.userLoading = false
      })
    },

    // 查询用户（支持远程搜索）
    queryUsers(query) {
      if (query !== undefined) {
        this.userQueryParams.nickName = query
        this.getUserList()
      }
    }
  }
}
</script>
