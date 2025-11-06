<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="计划编号" prop="planNo">
        <el-input
          v-model="queryParams.planNo"
          placeholder="请输入计划编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="设备名称" prop="equipName">
        <el-select v-model="queryParams.equipName" placeholder="请选择设备名称" clearable>
          <el-option
            v-for="dict in dict.type.equip_ledger"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="设备位号" prop="equipCode">
        <el-input
          v-model="queryParams.equipCode"
          placeholder="请输入设备位号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="按时执行" prop="isOnTime">
        <el-select v-model="queryParams.isOnTime" placeholder="请选择是否按时执行" clearable>
          <el-option
            v-for="dict in dict.type.sys_yes_no"
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
          v-hasPermi="['lubr:record2:add']"
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
          v-hasPermi="['lubr:record2:edit']"
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
          v-hasPermi="['lubr:record2:remove']"
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
          v-hasPermi="['lubr:record2:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="record2List" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="润滑记录ID" align="center" v-if="columns[0].visible" prop="recordId"/>
      <el-table-column label="计划ID" :show-overflow-tooltip="true" align="center" v-if="columns[1].visible"
                       prop="planId"
      />
      <el-table-column label="计划编号" :show-overflow-tooltip="true" align="center" v-if="columns[2].visible"
                       prop="planNo"
      />
      <el-table-column label="设备编号" :show-overflow-tooltip="true" align="center" v-if="columns[3].visible"
                       prop="equipId"
      />
      <el-table-column label="设备名称" align="center" v-if="columns[4].visible" prop="equipName"/>
      <el-table-column label="设备位号" :show-overflow-tooltip="true" align="center" v-if="columns[5].visible"
                       prop="equipCode"
      />
      <el-table-column label="管理级别" :show-overflow-tooltip="true" align="center" v-if="columns[6].visible"
                       prop="equipLevels"
      >
        <template slot-scope="scope">
          <dict-tag :options="dict.type.equip_levels" :value="scope.row.equipLevels"/>
          {{ scope.row.equipLevels }}
        </template>
      </el-table-column>
      <el-table-column label="实际数量(L)" :show-overflow-tooltip="true" align="center" v-if="columns[7].visible"
                       prop="actualQuantity"
      />
      <el-table-column label="润滑日期" align="center" v-if="columns[8].visible" prop="lubDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.lubDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="润滑人ID" align="center" v-if="columns[9].visible" prop="operatorId">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.equip_user" :value="scope.row.operatorId"/>
        </template>
      </el-table-column>
      <el-table-column label="润滑执行人" :show-overflow-tooltip="true" align="center" v-if="columns[10].visible"
                       prop="operatorName"
      />
      <el-table-column label="是否按时执行" align="center" v-if="columns[11].visible" prop="isOnTime">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_yes_no" :value="scope.row.isOnTime"/>
        </template>
      </el-table-column>
      <el-table-column label="延迟天数" :show-overflow-tooltip="true" align="center" v-if="columns[12].visible"
                       prop="delayDays"
      />
      <el-table-column label="延迟原因" :show-overflow-tooltip="true" align="center" v-if="columns[13].visible"
                       prop="delayReason"
      />
      <el-table-column label="执行情况" :show-overflow-tooltip="true" align="center" v-if="columns[14].visible"
                       prop="executionCondition"
      />
      <el-table-column label="现场照片" align="center" v-if="columns[15].visible" prop="appendix" width="100">
        <template slot-scope="scope">
          <image-preview :src="scope.row.appendix" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="复核人ID" align="center" v-if="columns[16].visible" prop="reviewerId"/>
      <el-table-column label="复核人" :show-overflow-tooltip="true" align="center" v-if="columns[17].visible"
                       prop="reviewerName"
      />
      <el-table-column label="备注" :show-overflow-tooltip="true" align="center" v-if="columns[18].visible"
                       prop="remark"
      />
      <el-table-column label="申请人ID" :show-overflow-tooltip="true" align="center" v-if="columns[19].visible"
                       prop="applyUserId"
      />
      <el-table-column label="申请人" :show-overflow-tooltip="true" align="center" v-if="columns[20].visible"
                       prop="applyUserName"
      />
      <el-table-column label="任务ID" :show-overflow-tooltip="true" align="center" v-if="columns[21].visible"
                       prop="taskId"
      />
      <el-table-column label="流程实例ID" align="center" v-if="columns[22].visible" prop="processInstanceId">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_process_category" :value="scope.row.processInstanceId"/>
        </template>
      </el-table-column>
      <el-table-column label="流程自定义ID" :show-overflow-tooltip="true" align="center" v-if="columns[23].visible"
                       prop="deployId"
      />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" fixed="right">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['lubr:record2:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleProcess(scope.row)"
            v-hasPermi="['lubr:record2:list']"
          >查看流程
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['lubr:record2:remove']"
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

    <!-- 添加或修改设备润滑记录对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="计划编号" prop="planId">
          <el-select v-model="form.planId" placeholder="请选择计划编号" @change="handlePlanChange">
            <el-option
              v-for="plan in planList"
              :key="plan.planId"
              :label="plan.planNo"
              :value="plan.planId"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="设备名称" prop="equipName">
          <el-input v-model="form.equipName" placeholder="请选择计划编号自动填充" disabled/>
        </el-form-item>
        <el-form-item label="设备位号" prop="equipCode">
          <el-input v-model="form.equipCode" placeholder="请选择计划编号自动填充" disabled/>
        </el-form-item>
        <el-form-item label="实际数量(L)" prop="actualQuantity">
          <el-input v-model="form.actualQuantity" placeholder="请输入实际数量(L)"/>
        </el-form-item>
        <el-form-item label="润滑日期" prop="lubDate">
          <el-date-picker clearable
                          v-model="form.lubDate"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择润滑日期"
          >
          </el-date-picker>
        </el-form-item>
        <!--        <el-form-item label="润滑人" prop="operatorId">-->
        <!--          <el-select v-model="form.operatorId" placeholder="请选择润滑人" multiple filterable remote-->
        <!--                     :remote-method="queryUsers" :loading="userLoading"-->
        <!--          >-->
        <!--            <el-option-->
        <!--              v-for="user in userList"-->
        <!--              :key="user.userId"-->
        <!--              :label="user.nickName"-->
        <!--              :value="user.userId"-->
        <!--            >-->
        <!--            </el-option>-->
        <!--          </el-select>-->
        <!--        </el-form-item>-->
        <el-form-item label="是否按时执行" prop="isOnTime">
          <el-select v-model="form.isOnTime" placeholder="请选择是否按时执行">
            <el-option
              v-for="dict in dict.type.sys_yes_no"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="延迟天数" prop="delayDays">
          <el-input v-model="form.delayDays" placeholder="请输入延迟天数"/>
        </el-form-item>
        <el-form-item label="延迟原因" prop="delayReason">
          <el-input v-model="form.delayReason" placeholder="请输入延迟原因"/>
        </el-form-item>
        <el-form-item label="执行情况" prop="executionCondition">
          <el-input v-model="form.executionCondition" placeholder="请输入执行情况"/>
        </el-form-item>
        <el-form-item label="现场照片" prop="appendix">
          <image-upload v-model="form.appendix"/>
        </el-form-item>
        <el-form-item label="复核人" prop="reviewerId">
          <el-select v-model="form.reviewerId" placeholder="请选择复核人" filterable remote :remote-method="queryUsers"
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
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容"/>
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
import {addRecord2, delRecord2, getRecord2, listRecord2, updateRecord2} from '@/api/lubr/record2'
import {listPlan} from '@/api/lubr/plan'
import {listUser} from '@/api/system/user'
import TaskDetail from '@/components/TaskDetail/index.vue'

export default {
  name: 'Record2',
  components: { TaskDetail },
  dicts: ['equip_user', 'equip_levels', 'sys_yes_no', 'equip_repair_sratus', 'equip_ledger', 'sys_process_category'],
  data() {
    return {
      //表格展示列
      columns: [
        { key: 0, label: '润滑记录ID', visible: true },
        { key: 1, label: '计划ID', visible: false },
        { key: 2, label: '计划编号', visible: true },
        { key: 3, label: '设备编号', visible: false },
        { key: 4, label: '设备名称', visible: true },
        { key: 5, label: '设备位号', visible: true },
        { key: 6, label: '管理级别', visible: true },
        { key: 7, label: '实际数量(L)', visible: true },
        { key: 8, label: '润滑日期', visible: true },
        { key: 9, label: '润滑人ID', visible: false },
        { key: 10, label: '润滑执行人', visible: true },
        { key: 11, label: '是否按时执行', visible: true },
        { key: 12, label: '延迟天数', visible: true },
        { key: 13, label: '延迟原因', visible: true },
        { key: 14, label: '执行情况', visible: true },
        { key: 15, label: '现场照片', visible: true },
        { key: 16, label: '复核人ID', visible: false },
        { key: 17, label: '复核人', visible: true },
        { key: 18, label: '备注', visible: false },
        { key: 19, label: '申请人ID', visible: false },
        { key: 20, label: '申请人', visible: false },
        { key: 21, label: '任务ID', visible: false },
        { key: 22, label: '流程实例ID', visible: false },
        { key: 23, label: '流程自定义ID', visible: false }
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
      // 设备润滑记录表格数据
      record2List: [],
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        planNo: null,
        equipName: null,
        equipCode: null,
        isOnTime: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        planId: [
          { required: true, message: '计划编号不能为空', trigger: 'change' }
        ]
      },
      // 润滑计划列表
      planList: [],
      // 用户列表
      userList: [],
      // 用户加载状态
      userLoading: false,
      // 用户查询参数
      userQueryParams: {
        pageNum: 1,
        pageSize: 100, // 增加pageSize以获取更多用户
        userName: undefined,
        nickName: undefined
      }
    }
  },
  created() {
    this.getList()
    this.getPlanList()
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
    /** 查询设备润滑记录列表 */
    getList() {
      this.loading = true
      listRecord2(this.queryParams).then(response => {
        this.record2List = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    // 获取润滑计划列表
    getPlanList() {
      listPlan({}).then(response => {
        this.planList = response.rows
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
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        recordId: null,
        planId: null,
        planNo: null,
        equipName: null,
        equipCode: null,
        equipLevels: null,
        actualQuantity: null,
        lubDate: null,
        operatorId: null,
        operatorName: null,
        isOnTime: null,
        delayDays: null,
        delayReason: null,
        executionCondition: null,
        appendix: null,
        reviewerId: null,
        reviewerName: null,
        remark: null,
        applyUserId: null,
        applyUserName: null,
        taskId: null,
        processInstanceId: null,
        deployId: null,
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
      this.ids = selection.map(item => item.recordId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = '添加设备润滑记录'
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const recordId = row.recordId || this.ids
      getRecord2(recordId).then(response => {
        this.form = response.data
        // 确保operatorId是数组格式，以便在多选框中正确显示
        if (this.form.operatorId && typeof this.form.operatorId === 'string') {
          // 如果是逗号分隔的字符串，转换为数组
          if (this.form.operatorId.includes(',')) {
            this.form.operatorId = this.form.operatorId.split(',').map(id => {
              const parsed = parseInt(id.trim())
              return isNaN(parsed) ? id.trim() : parsed
            })
          } else {
            // 单个ID也转换为数组
            const parsed = parseInt(this.form.operatorId)
            this.form.operatorId = [isNaN(parsed) ? this.form.operatorId : parsed]
          }
        } else if (this.form.operatorId && typeof this.form.operatorId === 'number') {
          // 单个数字ID转换为数组
          this.form.operatorId = [this.form.operatorId]
        } else if (!this.form.operatorId) {
          // 如果没有值，则初始化为空数组
          this.form.operatorId = []
        }
        //如果有审核人
        if (this.form.reviewerId) {
          this.form.reviewerId = parseInt(this.form.reviewerId)
        }
        this.open = true
        this.title = '修改设备润滑记录'
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          // 创建一个副本用于提交，避免修改原始表单数据
          const submitForm = Object.assign({}, this.form)

          // 处理多选的润滑人数据，将数组转换为逗号分隔的字符串
          if (Array.isArray(submitForm.operatorId) && submitForm.operatorId.length > 0) {
            submitForm.operatorId = submitForm.operatorId.join(',')

            // 根据选中的用户ID获取对应的用户名称
            const selectedUsers = this.userList.filter(user =>
              submitForm.operatorId.split(',').map(Number).includes(user.userId)
            )
            submitForm.operatorName = selectedUsers.map(user => user.nickName).join(',')
          } else {
            // 如果没有选择润滑人，设置为空字符串
            submitForm.operatorId = ''
            submitForm.operatorName = ''
          }

          // 处理复核人数据
          if (submitForm.reviewerId) {
            // 根据选中的复核人ID获取对应的用户名称
            const reviewer = this.userList.find(user => user.userId === submitForm.reviewerId)
            if (reviewer) {
              submitForm.reviewerName = reviewer.nickName
            }
          } else {
            submitForm.reviewerId = null
            submitForm.reviewerName = ''
          }

          if (this.form.recordId != null) {
            updateRecord2(submitForm).then(response => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.getList()
            }).catch(error => {
              this.$modal.msgError('修改失败: ' + (error.message || '请求数据格式错误'))
            })
          } else {
            addRecord2(submitForm).then(response => {
              this.$modal.msgSuccess('新增成功')
              this.open = false
              this.getList()
            }).catch(error => {
              this.$modal.msgError('新增失败: ' + (error.message || '请求数据格式错误'))
            })
          }
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const recordIds = row.recordId || this.ids
      this.$modal.confirm('是否确认删除设备润滑记录编号为"' + recordIds + '"的数据项？').then(function() {
        return delRecord2(recordIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess('删除成功')
      }).catch(() => {
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('lubr/record2/export', {
        ...this.queryParams
      }, `record2_${new Date().getTime()}.xlsx`)
    },
    // 计划编号选择变化
    handlePlanChange(val) {
      if (!val) {
        // 如果没有选择计划，清空相关字段
        this.$set(this.form, 'planNo', '')
        this.$set(this.form, 'equipName', '')
        this.$set(this.form, 'equipCode', '')
        return
      }

      // 查找选中的计划
      const selectedPlan = this.planList.find(plan => plan.planId === val)

      if (selectedPlan) {
        // 设置计划相关信息
        this.$set(this.form, 'planNo', selectedPlan.planNo || '')
        this.$set(this.form, 'equipName', selectedPlan.equipName || '')
        this.$set(this.form, 'equipCode', selectedPlan.equCode || '')
      }
    }

  }
}
</script>
