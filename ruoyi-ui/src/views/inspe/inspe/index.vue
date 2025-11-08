<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="检查类型" prop="inspeType">
        <el-select v-model="queryParams.inspeType" placeholder="请选择检查类型" clearable>
          <el-option
            v-for="dict in dict.type.equip_inspe"
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
          v-hasPermi="['inspe:inspe:add']"
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
          v-hasPermi="['inspe:inspe:edit']"
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
          v-hasPermi="['inspe:inspe:remove']"
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
          v-hasPermi="['inspe:inspe:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="inspeList" @selection-change="handleSelectionChange"
              :default-sort="defaultSort" @sort-change="handleSortChange"
    >
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="检查记录ID" align="center" v-if="columns[0].visible" prop="inspeId"/>
      <el-table-column label="检查单号" :show-overflow-tooltip="true" align="center" v-if="columns[1].visible"
                       prop="inspeNo"
      />
      <el-table-column label="检查日期" align="center" v-if="columns[2].visible" prop="inspeDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.inspeDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="检查类型" align="center" v-if="columns[3].visible" prop="inspeType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.equip_inspe" :value="scope.row.inspeType"/>
        </template>
      </el-table-column>
      <el-table-column label="检查组长" :show-overflow-tooltip="true" align="center" v-if="columns[4].visible"
                       prop="teamLeaderId"
      >
        <template slot-scope="scope">
          <span v-if="scope.row.teamLeaderId">{{
              getParticipantsNamesByIds(scope.row.teamLeaderId, 'participants')
            }}</span>
          <span v-else></span>
        </template>
      </el-table-column>
      <el-table-column label="参与人员" :show-overflow-tooltip="true" align="center" v-if="columns[5].visible"
                       prop="participantsIds"
      >
        <template slot-scope="scope">
          <span v-if="scope.row.participantsIds">{{
              getParticipantsNamesByIds(scope.row.participantsIds, 'participants')
            }}</span>
          <span v-else></span>
        </template>
      </el-table-column>
      <el-table-column label="有无隐患" align="center" v-if="columns[6].visible" prop="hasDanger">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.equip_has_danger" :value="scope.row.hasDanger"/>
        </template>
      </el-table-column>
      <el-table-column label="隐患数量" :show-overflow-tooltip="true" align="center" v-if="columns[7].visible"
                       prop="dangerCoun"
      />
      <el-table-column label="整体评价" align="center" v-if="columns[8].visible" prop="overallStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.equip_overall" :value="scope.row.overallStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="处理措施" :show-overflow-tooltip="true" align="center" v-if="columns[9].visible"
                       prop="processMeasures"
      />
      <el-table-column label="处理后照片" align="center" v-if="columns[10].visible" prop="processImage" width="100">
        <template slot-scope="scope">
          <image-preview :src="scope.row.processImage" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="处理人" :show-overflow-tooltip="true" align="center" v-if="columns[11].visible"
                       prop="processUserName"
      />
      <el-table-column label="备注" :show-overflow-tooltip="true" align="center" v-if="columns[12].visible"
                       prop="remark"
      />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['inspe:inspe:edit']"
          >修改
          </el-button>
          <!--          <el-button-->
          <!--            size="mini"-->
          <!--            type="text"-->
          <!--            icon="el-icon-view"-->
          <!--            @click="handleProcess(scope.row)"-->
          <!--            v-hasPermi="['inspe:inspe:list']"-->
          <!--          >查看流程-->
          <!--          </el-button>-->
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['inspe:inspe:remove']"
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

    <!-- 添加或修改设备检查记录对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="检查日期" prop="inspeDate">
          <el-date-picker clearable
                          v-model="form.inspeDate"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择检查日期"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="检查类型" prop="inspeType">
          <el-select v-model="form.inspeType" placeholder="请选择检查类型">
            <el-option
              v-for="dict in dict.type.equip_inspe"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="设备种类" prop="equipType">
          <el-select
            v-model="form.equipType"
            placeholder="请选择设备种类"
            @change="handleEquipTypeChange"
          >
            <el-option
              v-for="dict in dict.type.equip_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="设备名称" prop="equipName">
          <el-select
            v-model="form.equipName"
            placeholder="请选择设备名称"
            :disabled="pressurePipeOptions.length === 0 && form.equipType === 'pressure_pipe'"
          >
            <!-- 当设备种类为压力管道时，显示压力管道选项 -->
            <template v-if="form.equipType === 'pressure_pipe'">
              <el-option
                v-for="item in pressurePipeOptions"
                :key="item.pipelineId"
                :label="item.pipelineName"
                :value="item.pipelineName"
              >
                <span style="float: left">{{ item.pipelineName }}</span>
                <span style="float: right; color: #8492a6; font-size: 13px">{{ item.pipelineNo }}</span>
              </el-option>
            </template>
            <!-- 其他设备种类时，显示普通设备选项 -->
            <template v-else>
              <el-option
                v-for="item in equipOptions"
                :key="item.equipId"
                :label="item.equName"
                :value="item.equName"
              >
                <span style="float: left">{{ item.equName }}</span>
                <span style="float: right; color: #8492a6; font-size: 13px">{{ item.equCode }}</span>
              </el-option>
            </template>
          </el-select>
        </el-form-item>
        <el-form-item label="检查组长" prop="teamLeaderId">
          <el-select
            v-model="form.teamLeaderId"
            placeholder="请选择检查组长"
            filterable
            remote
            :remote-method="queryInspectors"
            :loading="inspectorLoading"
            @focus="loadAllInspectors"
          >
            <el-option
              v-for="item in inspectorOptions"
              :key="item.userId"
              :label="item.nickName"
              :value="item.userId"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="参与人员" prop="participantsIds">
          <el-select
            v-model="form.participantsIds"
            placeholder="请选择参与人员"
            filterable
            remote
            :remote-method="queryParticipants"
            :loading="participantsLoading"
            @focus="loadAllParticipants"
            multiple
          >
            <el-option
              v-for="item in participantsOptions"
              :key="item.userId.toString()"
              :label="item.nickName"
              :value="item.userId.toString()"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="有无隐患" prop="hasDanger">
          <el-select v-model="form.hasDanger" placeholder="请选择有无隐患">
            <el-option
              v-for="dict in dict.type.equip_has_danger"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="隐患数量" prop="dangerCoun">
          <el-input v-model="form.dangerCoun" placeholder="请输入隐患数量"/>
        </el-form-item>
        <el-form-item label="整体评价" prop="overallStatus">
          <el-select v-model="form.overallStatus" placeholder="请选择整体评价">
            <el-option
              v-for="dict in dict.type.equip_overall"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="处理措施" prop="processMeasures">
          <el-input v-model="form.processMeasures" type="textarea" placeholder="请输入内容"/>
        </el-form-item>
        <el-form-item label="处理后照片" prop="processImage">
          <image-upload v-model="form.processImage"/>
        </el-form-item>
        <el-form-item label="处理人" prop="processUserId">
          <el-select
            v-model="form.processUserId"
            placeholder="请选择处理人"
            filterable
            remote
          >
            <el-option
              v-for="item in processUserList"
              :key="item.userId"
              :label="item.userName"
              :value="item.userId"
            >
            </el-option>
          </el-select>
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
    <!--    <TaskDetail :visible.sync="dialogVisible"-->
    <!--                :task-id="taskId"-->
    <!--                :proc-ins-id="procInsId"-->
    <!--                :deploy-id="deployId"-->
    <!--    />-->
  </div>
</template>

<script>
import { listInspe, getInspe, delInspe, addInspe, updateInspe } from '@/api/inspe/inspe'
import { listUser } from '@/api/system/user'
import TaskDetail from '@/components/TaskDetail/index.vue'
import { listData } from '@/api/special/data'
import { listLedger } from '@/api/equip/ledger'

export default {
  name: 'Inspe',
  components: { TaskDetail },
  dicts: ['equip_user', 'equip_has_danger', 'equip_overall', 'equip_repair_sratus', 'equip_inspe', 'sys_process_category', 'equip_type'],
  data() {
    return {
      //表格展示列
      columns: [
        { key: 0, label: '检查记录ID', visible: true },
        { key: 1, label: '检查单号', visible: true },
        { key: 2, label: '检查日期', visible: true },
        { key: 3, label: '检查类型', visible: true },
        { key: 4, label: '检查组长', visible: true },
        { key: 5, label: '参与人员', visible: true },
        { key: 6, label: '有无隐患', visible: true },
        { key: 7, label: '隐患数量', visible: true },
        { key: 8, label: '整体评价', visible: true },
        { key: 9, label: '处理措施', visible: true },
        { key: 10, label: '处理后照片', visible: true },
        { key: 11, label: '处理人', visible: true },
        { key: 12, label: '备注', visible: true }
      ],
      dialogVisible: false,
      deployId: '',
      procInsId: '',
      taskId: '',
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 子表选中数据
      checkedEquipDeviceItem: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 设备检查记录表格数据
      inspeList: [],
      // 检查组长选项
      inspectorOptions: [],
      // 检查组长加载状态
      inspectorLoading: false,
      // 参与人员选项
      participantsOptions: [],
      // 参与人员加载状态
      participantsLoading: false,
      // 所有用户缓存
      allUsersCache: [],
      // 缓存加载状态
      allUsersCacheLoading: false,
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 默认排序
      defaultSort: { prop: 'inspeDate', order: 'descending' },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        inspeType: null,
        orderByColumn: 'inspe_date',
        isAsc: 'desc'
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
      // 压力管道选项
      pressurePipeOptions: [],
      // 设备选项
      equipOptions: [],
      processUserList: []
    }
  },
  created() {
    this.getList()
    this.loadAllUsersCache() // 加载所有用户缓存
    // 初始化加载设备数据
    this.getEquipments()
    this.getProcessUserList()
  },
  methods: {
    getProcessUserList() {
      listUser({ pageNum: 1, pageSize: 1000 }).then(response => {
        this.processUserList = response.rows
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
    /** 设备种类变化处理 */
    handleEquipTypeChange(value) {
      // 如果选择的是压力管道
      if (value === 'pressure_pipe') {
        // 获取压力管道数据（是否压力管道为"是"的管道）
        this.getPressurePipes()
      } else {
        // 获取普通设备数据
        this.getEquipments()
      }

      // 清空已选择的设备名称
      this.form.equipName = null
    },
    /** 获取压力管道数据 */
    getPressurePipes() {
      // 调用API获取"是否压力管道"为"是"的管道数据
      listData({ pressurePipeline: 'Y' }).then(response => {
        this.pressurePipeOptions = response.rows
      }).catch(() => {
        this.$message.warning('无法加载压力管道数据')
      })
    },
    /** 获取普通设备数据 */
    getEquipments() {
      // 获取普通设备数据
      listLedger({ pageSize: 1000 }).then(response => {
        this.equipOptions = response.rows
      }).catch(() => {
        this.$message.warning('无法加载设备数据')
      })
    },
    /** 查询设备检查记录列表 */
    getList() {
      this.loading = true
      listInspe(this.queryParams).then(response => {
        this.inspeList = response.rows
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
        inspeId: null,
        inspeNo: null,
        inspeDate: null,
        inspeType: null,
        teamLeaderId: null,
        participantsIds: null,
        hasDanger: null,
        dangerCoun: null,
        appendix: null,
        overallStatus: null,
        deptId: null,
        deptName: null,
        applyUserId: null,
        applyUserName: null,
        deployId: null,
        processInstanceId: null,
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
      this.queryParams.orderByColumn = 'inspe_date'
      this.queryParams.isAsc = 'desc'
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.inspeId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = '添加设备检查记录'
      // 初始化检查组长和参与人员选项
      this.loadAllInspectors()
      this.loadAllParticipants()
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const inspeId = row.inspeId || this.ids
      getInspe(inspeId).then(response => {
        this.form = response.data
        this.open = true
        this.title = '修改设备检查记录'
        // 初始化检查组长和参与人员选项
        this.loadAllInspectors()
        this.loadAllParticipants()

        // 确保当前选中的检查组长和参与人员在选项中
        if (this.form.teamLeaderId && this.form.teamLeaderId !== '') {
          // 检查当前选中的检查组长是否在选项中
          const exists = this.inspectorOptions.some(option => String(option.userId) === String(this.form.teamLeaderId))
          this.form.teamLeaderId = Number(this.form.teamLeaderId)
          if (!exists) {
            // 如果当前选中的检查组长不在选项中，则添加
            listUser({ userId: this.form.teamLeaderId }).then(res => {
              if (res.data) {
                this.inspectorOptions.push(res.data)
              }
            }).catch(() => {
              // 忽略错误
            })
          }
        }

        // 确保当前选中的参与人员在选项中
        if (this.form.participantsIds) {
          try {
            //转换为数组
            var currentParticipants
            //如果是字符串并且以[开头
            if (typeof this.form.participantsIds === 'string' && this.form.participantsIds.startsWith('[')) {
              currentParticipants = JSON.parse(this.form.participantsIds)
            } else {
              currentParticipants = this.form.participantsIds.split(',')
            }
            currentParticipants.forEach(participantId => {
              const exists = this.participantsOptions.some(option => String(option.userId) === String(participantId))
              if (!exists) {
                console.log('当前选中的参与人员：', participantId)
                // 如果当前选中的参与人员不在选项中，则添加
                listUser({ userId: Number(participantId) }).then(res => {
                  if (res.data) {
                    this.participantsOptions.push(res.data)
                  }
                }).catch(() => {
                  // 忽略错误
                })
              }
            })
          } catch (e) {
            // 忽略解析错误
            console.error(e)
          }
        }
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          // 处理检查组长和参与人员的值
          if (this.form.teamLeaderId) {
            // 从选项中查找检查组长名称
            const inspector = this.inspectorOptions.find(u => String(u.userId) === String(this.form.teamLeaderId))
            if (inspector) {
              // 保存检查组长名称
              this.form.teamLeader = inspector.nickName
            }
          }

          if (this.form.participantsIds && Array.isArray(this.form.participantsIds)) {
            // 从选项中查找参与人员名称
            const participantNames = this.participantsOptions
              .filter(u => this.form.participantsIds.map(String).includes(String(u.userId)))
              .map(u => u.nickName)
            // 保存参与人员ID列表为逗号分隔的字符串
            this.form.participantsIds = this.form.participantsIds.join(',')
            // 保存参与人员名称列表
            this.form.participants = participantNames.join(',')
          }

          // 确保 teamLeaderId 是数字类型
          if (this.form.teamLeaderId && typeof this.form.teamLeaderId !== 'number') {
            this.form.teamLeaderId = parseInt(this.form.teamLeaderId)
          }

          if (this.form.inspeId != null) {
            updateInspe(this.form).then(response => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.getList()
            }).catch(error => {
              this.$modal.msgError('修改失败：' + (error.message || '未知错误'))
            })
          } else {
            addInspe(this.form).then(response => {
              this.$modal.msgSuccess('新增成功')
              this.open = false
              this.getList()
            }).catch(error => {
              this.$modal.msgError('新增失败：' + (error.message || '未知错误'))
            })
          }
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const inspeIds = row.inspeId || this.ids
      this.$modal.confirm('是否确认删除设备检查记录编号为"' + inspeIds + '"的数据项？').then(function() {
        return delInspe(inspeIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess('删除成功')
      }).catch(() => {
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('inspe/inspe/export', {
        ...this.queryParams
      }, `inspe_${new Date().getTime()}.xlsx`)
    },
    // 查询检查组长
    queryInspectors(query) {
      this.inspectorLoading = true
      listUser({ nickName: query || undefined }).then(response => {
        this.inspectorOptions = response.rows || []
        this.inspectorLoading = false
      }).catch(() => {
        this.inspectorLoading = false
        this.inspectorOptions = []
      })
    },
    // 查询参与人员
    queryParticipants(query) {
      this.participantsLoading = true
      listUser({ nickName: query || undefined }).then(response => {
        this.participantsOptions = response.rows || []
        this.participantsLoading = false
      }).catch(() => {
        this.participantsLoading = false
        this.participantsOptions = []
      })
    },
    // 加载所有检查组长
    loadAllInspectors() {
      this.inspectorLoading = true
      listUser({ pageSize: 10000 }).then(response => {
        this.inspectorOptions = response.rows || []
        this.inspectorLoading = false
      }).catch(() => {
        this.inspectorLoading = false
        this.inspectorOptions = []
      })
    },
    // 加载所有参与人员
    loadAllParticipants() {
      this.participantsLoading = true
      listUser({ pageSize: 10000 }).then(response => {
        this.participantsOptions = response.rows || []
        this.participantsLoading = false
      }).catch(() => {
        this.participantsLoading = false
        this.participantsOptions = []
      })
    },
    // 加载所有用户缓存
    loadAllUsersCache() {
      if (this.allUsersCacheLoading) return
      this.allUsersCacheLoading = true
      listUser({ pageSize: 10000 }).then(response => {
        this.allUsersCache = response.rows || []
        this.allUsersCacheLoading = false
      }).catch(() => {
        this.allUsersCacheLoading = false
        this.allUsersCache = []
      })
    },
    // 根据ID获取用户昵称
    getUserNickNameById(userId, type) {
      if (!userId) return ''

      // 确保userId是数字类型
      const id = typeof userId === 'string' ? parseInt(userId) : userId

      // 先从缓存中查找
      const user = this.allUsersCache.find(u => u.userId === id)
      if (user) {
        return user.nickName
      }

      // 如果缓存中没有，尝试从选项中查找
      let options = []
      if (type === 'inspector') {
        options = this.inspectorOptions
      } else if (type === 'participants') {
        options = this.participantsOptions
      }

      const option = options.find(o => o.userId === id)
      if (option) {
        return option.nickName
      }

      // 如果都没有找到，重新加载缓存
      this.loadAllUsersCache()
      return userId // 返回ID作为后备
    },
    // 根据IDs获取参与人员名称
    getParticipantsNamesByIds(participantsIds, type) {
      if (!participantsIds) return ''

      try {
        // 解析ID列表
        let ids = []
        if (typeof participantsIds === 'string') {
          ids = participantsIds.split(',').map(id => parseInt(id.trim())).filter(id => !isNaN(id))
        } else if (Array.isArray(participantsIds)) {
          ids = participantsIds.map(id => typeof id === 'string' ? parseInt(id) : id).filter(id => !isNaN(id))
        }

        // 获取每个ID对应的昵称
        const names = ids.map(id => this.getUserNickNameById(id, type)).filter(name => name)
        return names.join(', ')
      } catch (e) {
        console.error('解析参与人员ID时出错:', e)
        return participantsIds
      }
    },
    /** 处理排序变化 */
    handleSortChange(column) {
      const { prop, order } = column
      if (prop === 'inspeDate') {
        this.queryParams.orderByColumn = 'inspe_date'
        this.queryParams.isAsc = order === 'ascending' ? 'asc' : 'desc'
        this.getList()
      }
    }
  }
}
</script>
