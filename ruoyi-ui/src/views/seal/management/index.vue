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
      <el-form-item label="严重程度" prop="severity">
        <el-select v-model="queryParams.severity" placeholder="请选择泄漏严重程度" clearable>
          <el-option
            v-for="dict in dict.type.equip_severity"
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
          v-hasPermi="['seal:management:add']"
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
          v-hasPermi="['seal:management:edit']"
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
          v-hasPermi="['seal:management:remove']"
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
          v-hasPermi="['seal:management:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="managementList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="主键ID" align="center" v-if="columns[0].visible" prop="manageId"/>
      <el-table-column label="设备ID" align="center" v-if="columns[1].visible" prop="equipId"/>
      <el-table-column label="设备名称" :show-overflow-tooltip="true" align="center" v-if="columns[2].visible"
                       prop="equipName"
      />
      <el-table-column label="设备位号" :show-overflow-tooltip="true" align="center" v-if="columns[3].visible"
                       prop="equipCode"
      />
      <el-table-column label="设备管理级别" align="center" v-if="columns[4].visible" prop="equipLevels">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.equip_levels" :value="scope.row.equipLevels"/>
        </template>
      </el-table-column>
      <el-table-column label="泄漏部位" :show-overflow-tooltip="true" align="center" v-if="columns[5].visible"
                       prop="leakLocation"
      />
      <el-table-column label="发现时间" align="center" v-if="columns[6].visible" prop="discoveryTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.discoveryTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="报告人id" align="center" v-if="columns[7].visible" prop="reporterId"/>
      <el-table-column label="报告人" :show-overflow-tooltip="true" align="center" v-if="columns[8].visible"
                       prop="reporterName"
      />
      <el-table-column label="泄漏严重程度" align="center" v-if="columns[9].visible" prop="severity">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.equip_severity" :value="scope.row.severity"/>
        </template>
      </el-table-column>
      <el-table-column label="泄漏介质" :show-overflow-tooltip="true" align="center" v-if="columns[10].visible"
                       prop="leakedMedium"
      />
      <el-table-column label="现场描述" :show-overflow-tooltip="true" align="center" v-if="columns[11].visible"
                       prop="description"
      />
      <el-table-column label="现场照片" align="center" v-if="columns[12].visible" prop="appendix" width="100">
        <template slot-scope="scope">
          <image-preview :src="scope.row.appendix" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="处理措施" :show-overflow-tooltip="true" align="center" v-if="columns[13].visible"
                       prop="handlingMeasures"
      />
      <el-table-column label="处理人id" align="center" v-if="columns[14].visible" prop="handlerId"/>
      <el-table-column label="处理人" :show-overflow-tooltip="true" align="center" v-if="columns[15].visible"
                       prop="handlerName"
      />
      <el-table-column label="完成时间" align="center" v-if="columns[16].visible" prop="finishTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.finishTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="处理后照片" align="center" v-if="columns[17].visible" prop="processImage" width="100">
        <template slot-scope="scope">
          <image-preview :src="scope.row.processImage" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="备注" :show-overflow-tooltip="true" align="center" v-if="columns[18].visible"
                       prop="remark"
      />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" fixed="right">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['seal:management:edit']"
          >修改
          </el-button>
          <!--          <el-button-->
          <!--            size="mini"-->
          <!--            type="text"-->
          <!--            icon="el-icon-view"-->
          <!--            @click="handleProcess(scope.row)"-->
          <!--            v-hasPermi="['seal:management:list']"-->
          <!--          >查看流程-->
          <!--          </el-button>-->
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['seal:management:remove']"
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

    <!-- 添加或修改泄露处置对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="900px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="设备" prop="equipId">
          <el-row>
            <el-col :span="20">
              <el-select
                v-model="form.equipId"
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
        <el-form-item label="管理级别" prop="equipLevels">
          <el-select v-model="form.equipLevels" placeholder="请选择管理级别" disabled>
            <el-option
              v-for="dict in dict.type.equip_levels"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="泄漏部位" prop="leakLocation">
          <el-input v-model="form.leakLocation" placeholder="请输入泄漏部位"/>
        </el-form-item>
        <el-form-item label="发现时间" prop="discoveryTime">
          <el-date-picker clearable
                          v-model="form.discoveryTime"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择发现时间"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="报告人" prop="reporterId">
          <el-select v-model="form.reporterId" placeholder="请选择报告人" filterable remote :remote-method="queryUsers"
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
        <el-form-item label="泄漏严重程度" prop="severity">
          <el-select v-model="form.severity" placeholder="请选择泄漏严重程度">
            <el-option
              v-for="dict in dict.type.equip_severity"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="泄漏介质" prop="leakedMedium">
          <el-input v-model="form.leakedMedium" placeholder="请输入泄漏介质"/>
        </el-form-item>
        <el-form-item label="现场描述" prop="description">
          <el-input v-model="form.description" type="textarea" placeholder="请输入内容"/>
        </el-form-item>
        <el-form-item label="现场照片" prop="appendix">
          <file-upload v-model="form.appendix" :limit="10" :file-type="['png', 'jpg', 'jpeg']"/>
        </el-form-item>
        <el-form-item label="处理人" prop="handlerId">
          <el-select v-model="form.handlerId" placeholder="请选择处理人"
          >
            <el-option
              v-for="user in handleUserList"
              :key="user.userId"
              :label="user.userName"
              :value="user.userId"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="处理措施" prop="handlingMeasures">
          <el-input v-model="form.handlingMeasures" type="textarea" placeholder="请输入内容"/>
        </el-form-item>
        <el-form-item label="完成时间" prop="finishTime">
          <el-date-picker clearable
                          v-model="form.finishTime"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择完成时间"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="处理后照片" prop="processImage">
          <image-upload v-model="form.processImage"/>
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
    <!--    <TaskDetail :visible.sync="dialogVisible"-->
    <!--                :task-id="taskId"-->
    <!--                :proc-ins-id="procInsId"-->
    <!--                :deploy-id="deployId"-->
    <!--    />-->
  </div>
</template>

<script>
import { listManagement, getManagement, delManagement, addManagement, updateManagement } from '@/api/seal/management'
import { listLedger } from '@/api/equip/ledger'
import { listUser } from '@/api/system/user'
import TaskDetail from '@/components/TaskDetail/index.vue'

export default {
  name: 'Management',
  components: { TaskDetail },
  dicts: ['equip_levels', 'equip_user', 'equip_severity', 'equip_repair_sratus', 'equip_ledger', 'sys_process_category', 'equip_type', 'equip_location'],
  data() {
    return {
      //表格展示列
      columns: [
        { key: 0, label: '主键ID', visible: true },
        { key: 1, label: '设备ID', visible: false },
        { key: 2, label: '设备名称', visible: true },
        { key: 3, label: '设备位号', visible: true },
        { key: 4, label: '设备管理级别', visible: true },
        { key: 5, label: '泄漏部位', visible: true },
        { key: 6, label: '发现时间', visible: true },
        { key: 7, label: '报告人id', visible: false },
        { key: 8, label: '报告人', visible: true },
        { key: 9, label: '泄漏严重程度', visible: true },
        { key: 10, label: '泄漏介质', visible: true },
        { key: 11, label: '现场描述', visible: true },
        { key: 12, label: '现场照片', visible: true },
        { key: 13, label: '处理措施', visible: true },
        { key: 14, label: '处理人id', visible: false },
        { key: 15, label: '处理人', visible: true },
        { key: 16, label: '完成时间', visible: true },
        { key: 17, label: '处理后照片', visible: true },
        { key: 18, label: '备注', visible: false }
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
      // 泄露处置表格数据
      managementList: [],
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
        equipName: null,
        severity: null
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
        equipId: [
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
      },
      //处理人
      handleUserList: [],
      handlerUserQuery: {
        pageNum: 1,
        pageSize: 1000,
        nickName: undefined
      }
    }
  },
  created() {
    this.getList()
    this.getEquipList()
    this.getUserList()
    this.getHandleUserList()
  },
  methods: {
    //处理人
    getHandleUserList() {
      listUser(this.handleUserList).then(response => {
        this.handleUserList = response.rows
      })
    },
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
    /** 查询泄露处置列表 */
    getList() {
      this.loading = true
      listManagement(this.queryParams).then(response => {
        this.managementList = response.rows
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
        manageId: null,
        equipId: null,
        equipName: null,
        equipCode: null,
        equipLevels: null,
        leakLocation: null,
        discoveryTime: null,
        reporterId: null,
        reporterName: null,
        severity: null,
        leakedMedium: null,
        description: null,
        appendix: null,
        handlingMeasures: null,
        handlerId: null,
        handlerName: null,
        finishTime: null,
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
      this.ids = selection.map(item => item.manageId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = '添加泄露处置'
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const manageId = row.manageId || this.ids
      getManagement(manageId).then(response => {
        this.form = response.data
        if (this.form.handlerId) {
          this.form.handlerId = Number(this.form.handlerId)
        }
        this.open = true
        this.title = '修改泄露处置'
      })
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
    },

    /** 提交按钮 */
    submitForm() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          // 处理报告人姓名
          if (this.form.reporterId) {
            const selectedUser = this.userList.find(user => user.userId === this.form.reporterId)
            if (selectedUser) {
              this.form.reporterName = selectedUser.nickName
            }
          }

          if (this.form.manageId != null) {
            updateManagement(this.form).then(response => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addManagement(this.form).then(response => {
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
      const manageIds = row.manageId || this.ids
      this.$modal.confirm('是否确认删除泄露处置编号为"' + manageIds + '"的数据项？').then(function() {
        return delManagement(manageIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess('删除成功')
      }).catch(() => {
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('seal/management/export', {
        ...this.queryParams
      }, `management_${new Date().getTime()}.xlsx`)
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
      this.form.equipId = row.equipId
      this.form.equipName = row.equName
      this.form.equCode = row.equCode
      this.form.equipLevels = row.equipLevels
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
        this.form.equipLevels = selectedEquip.equipLevels
      }
    }
  }
}
</script>
