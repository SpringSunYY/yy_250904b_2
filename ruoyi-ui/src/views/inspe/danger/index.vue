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
      <el-form-item label="责任人" prop="responName">
        <el-input
          v-model="queryParams.responName"
          placeholder="请输入责任人"
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
          v-hasPermi="['inspe:danger:add']"
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
          v-hasPermi="['inspe:danger:edit']"
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
          v-hasPermi="['inspe:danger:remove']"
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
          v-hasPermi="['inspe:danger:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="dangerList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="隐患ID" align="center" v-if="columns[0].visible" prop="dangerId"/>
      <el-table-column label="检查记录ID" :show-overflow-tooltip="true" align="center" v-if="columns[1].visible"
                       prop="inspeId"
      />
      <el-table-column label="检查单号" :show-overflow-tooltip="true" align="center" v-if="columns[2].visible"
                       prop="inspeNo"
      />
      <el-table-column label="隐患编号" :show-overflow-tooltip="true" align="center" v-if="columns[3].visible"
                       prop="dangerNo"
      />
      <el-table-column label="设备id" align="center" v-if="columns[4].visible" prop="equipId">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.equip_ledger" :value="scope.row.equipId"/>
        </template>
      </el-table-column>
      <el-table-column label="设备名称" :show-overflow-tooltip="true" align="center" v-if="columns[5].visible"
                       prop="equipName"
      />
      <el-table-column label="设备位号" :show-overflow-tooltip="true" align="center" v-if="columns[6].visible"
                       prop="equipCode"
      />
      <el-table-column label="隐患等级" align="center" v-if="columns[7].visible" prop="dangerLevel">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.equip_danger_level" :value="scope.row.dangerLevel"/>
        </template>
      </el-table-column>
      <el-table-column label="隐患描述" :show-overflow-tooltip="true" align="center" v-if="columns[8].visible"
                       prop="description"
      />
      <el-table-column label="隐患照片" align="center" v-if="columns[9].visible" prop="appendix" width="100">
        <template slot-scope="scope">
          <image-preview :src="scope.row.appendix" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="责任人ID" align="center" v-if="columns[10].visible" prop="responId"/>
      <el-table-column label="责任人" :show-overflow-tooltip="true" align="center" v-if="columns[11].visible"
                       prop="responName"
      />
      <el-table-column label="整改期限" align="center" v-if="columns[12].visible" prop="deadline" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.deadline, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="验收时间" align="center" v-if="columns[13].visible" prop="approvalTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.approvalTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="验收结果" :show-overflow-tooltip="true" align="center" v-if="columns[14].visible"
                       prop="approvalResult"
      />
      <el-table-column label="奖励用户" :show-overflow-tooltip="true" align="center" v-if="columns[15].visible"
                       prop="awardUserName"
      />
      <el-table-column label="奖励部门" :show-overflow-tooltip="true" align="center" v-if="columns[16].visible"
                       prop="awardDeptName"
      />
      <el-table-column label="奖励金额" :show-overflow-tooltip="true" align="center" v-if="columns[17].visible"
                       prop="awardAmount"
      />
      <el-table-column label="惩罚用户" :show-overflow-tooltip="true" align="center" v-if="columns[18].visible"
                       prop="punishUserName"
      />
      <el-table-column label="惩罚部门" :show-overflow-tooltip="true" align="center" v-if="columns[19].visible"
                       prop="punishDeptName"
      />
      <el-table-column label="惩罚金额" :show-overflow-tooltip="true" align="center" v-if="columns[20].visible"
                       prop="punishAmount"
      />
      <el-table-column label="申请部门ID" :show-overflow-tooltip="true" align="center" v-if="columns[21].visible"
                       prop="deptId"
      />
      <el-table-column label="申请部门" :show-overflow-tooltip="true" align="center" v-if="columns[22].visible"
                       prop="deptName"
      />
      <el-table-column label="申请人ID" :show-overflow-tooltip="true" align="center" v-if="columns[23].visible"
                       prop="applyUserId"
      />
      <el-table-column label="申请人" :show-overflow-tooltip="true" align="center" v-if="columns[24].visible"
                       prop="applyUserName"
      />
      <el-table-column label="任务ID" :show-overflow-tooltip="true" align="center" v-if="columns[25].visible"
                       prop="taskId"
      />
      <el-table-column label="流程实例ID" align="center" v-if="columns[26].visible" prop="processInstanceId">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_process_category" :value="scope.row.processInstanceId"/>
        </template>
      </el-table-column>
      <el-table-column label="流程自定义ID" :show-overflow-tooltip="true" align="center" v-if="columns[27].visible"
                       prop="deployId"
      />
      <el-table-column label="备注" :show-overflow-tooltip="true" align="center" v-if="columns[28].visible"
                       prop="remark"
      />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" fixed="right">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['inspe:danger:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleProcess(scope.row)"
            v-hasPermi="['inspe:danger:list']"
          >查看流程
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['inspe:danger:remove']"
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

    <!-- 添加或修改设备隐患治理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="900px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="检查单号" prop="inspeId">
          <el-select v-model="form.inspeId" placeholder="请选择检查单号" clearable filterable>
            <el-option
              v-for="item in inspeOptions"
              :key="item.inspeId"
              :label="item.inspeNo"
              :value="item.inspeId"
            >
            </el-option>
          </el-select>
        </el-form-item>
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
        <el-form-item label="设备位号" prop="equipCode">
          <el-input v-model="form.equipCode" placeholder="设备位号" disabled/>
        </el-form-item>
        <el-form-item label="隐患等级" prop="dangerLevel">
          <el-select v-model="form.dangerLevel" placeholder="请选择隐患等级" clearable>
            <el-option
              v-for="dict in dict.type.equip_danger_level"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <!--        <el-form-item label="有无隐患" prop="isDanger">-->
        <!--          <el-select v-model="form.isDanger" placeholder="请选择有无隐患" clearable>-->
        <!--            <el-option-->
        <!--              v-for="dict in dict.type.equip_has_danger"-->
        <!--              :key="Number(dict.value)"-->
        <!--              :label="dict.label"-->
        <!--              :value="Number(dict.value)"-->
        <!--            />-->
        <!--          </el-select>-->
        <!--        </el-form-item>-->
        <el-form-item label="隐患描述" prop="description">
          <el-input v-model="form.description" type="textarea" placeholder="请输入内容"/>
        </el-form-item>
        <el-form-item label="隐患照片" prop="appendix">
          <image-upload v-model="form.appendix"/>
        </el-form-item>
        <el-form-item label="责任人" prop="responId">
          <el-select
            v-model="form.responId"
            placeholder="请选择责任人"
            clearable
            filterable
            remote
            :remote-method="queryUsers"
            :loading="userLoading"
          >
            <el-option
              v-for="item in userOptions"
              :key="item.userId"
              :label="item.nickName"
              :value="item.userId"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="整改期限" prop="deadline">
          <el-date-picker clearable
                          v-model="form.deadline"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择整改期限"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="奖励用户" prop="awardUserId">
          <el-select
            v-model="form.awardUserId"
            placeholder="请选择奖励用户"
            clearable
            filterable
            remote
            :remote-method="queryUsers"
            :loading="userLoading"
          >
            <el-option
              v-for="item in userOptions"
              :key="item.userId"
              :label="item.nickName"
              :value="item.userId"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="奖励金额" prop="awardAmount">
          <el-input-number :min="0" :precision="2" v-model="form.awardAmount" placeholder="请输入奖励金额"/>
        </el-form-item>
        <el-form-item label="惩罚用户" prop="punishUserId">
          <el-select
            v-model="form.punishUserId"
            placeholder="请选择惩罚"
            clearable
            filterable
            remote
            :remote-method="queryUsers"
            :loading="userLoading"
          >
            <el-option
              v-for="item in userOptions"
              :key="item.userId"
              :label="item.nickName"
              :value="item.userId"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="惩罚金额" prop="punishAmount">
          <el-input-number :min="0" :precision="2" v-model="form.punishAmount" placeholder="请输入惩罚金额"/>
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
import { addDanger, delDanger, getDanger, listDanger, updateDanger } from '@/api/inspe/danger'
import { listInspe } from '@/api/inspe/inspe'
import { listLedger } from '@/api/equip/ledger'
import { listUser } from '@/api/system/user'
import TaskDetail from '@/components/TaskDetail/index.vue'

export default {
  name: 'Danger',
  components: { TaskDetail },
  dicts: ['equip_has_danger', 'equip_danger_level', 'equip_user', 'equip_repair_sratus', 'equip_type', 'equip_levels', 'equip_location'],
  data() {
    return {
      //表格展示列
      columns: [
        { key: 0, label: '隐患ID', visible: false },
        { key: 1, label: '检查记录ID', visible: false },
        { key: 2, label: '检查单号', visible: true },
        { key: 3, label: '隐患编号', visible: true },
        { key: 4, label: '设备id', visible: false },
        { key: 5, label: '设备名称', visible: true },
        { key: 6, label: '设备位号', visible: true },
        // { key: 7, label: '有无隐患', visible: true },
        { key: 7, label: '隐患等级', visible: true },
        { key: 8, label: '隐患描述', visible: true },
        { key: 9, label: '隐患照片', visible: true },
        { key: 10, label: '责任人ID', visible: false },
        { key: 11, label: '责任人', visible: true },
        { key: 12, label: '整改期限', visible: true },
        { key: 13, label: '验收时间', visible: true },
        { key: 14, label: '验收结果', visible: true },
        { key: 15, label: '奖励用户', visible: true },
        { key: 16, label: '奖励部门', visible: true },
        { key: 17, label: '奖励金额', visible: true },
        { key: 18, label: '惩罚用户', visible: true },
        { key: 19, label: '惩罚部门', visible: true },
        { key: 20, label: '惩罚金额', visible: true },
        { key: 21, label: '申请部门ID', visible: false },
        { key: 22, label: '申请部门', visible: false },
        { key: 23, label: '申请人ID', visible: false },
        { key: 24, label: '申请人', visible: false },
        { key: 25, label: '任务ID', visible: false },
        { key: 26, label: '流程实例ID', visible: false },
        { key: 27, label: '流程自定义ID', visible: false },
        { key: 28, label: '备注', visible: false }
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
      // 设备隐患治理表格数据
      dangerList: [],
      // 检查记录选项
      inspeOptions: [],
      // 设备列表
      equipList: [],
      // 设备选择器列表
      equipListSelector: [],
      // 设备总数
      equipTotal: 0,
      // 设备加载状态
      equipLoading: false,
      // 用户选项
      userOptions: [],
      // 用户加载状态
      userLoading: false,
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
        responName: null
      },
      // 设备查询参数
      equipQueryParams: {
        pageNum: 1,
        pageSize: 10,
        equName: undefined,
        equCode: undefined
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        equipId: [
          { required: true, message: '设备不能为空', trigger: 'change' }
        ],
        isDanger: [
          { required: true, message: '有无隐患不能为空', trigger: 'change' }
        ]
      }
    }
  },
  created() {
    this.getList()
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
    /** 查询设备隐患治理列表 */
    getList() {
      this.loading = true
      listDanger(this.queryParams).then(response => {
        this.dangerList = response.rows
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
        dangerId: null,
        inspeId: null,
        inspeNo: null,
        dangerNo: null,
        equipId: null,
        equipName: null,
        equipCode: null,
        isDanger: null,
        dangerLevel: null,
        description: null,
        responId: null,
        responName: null,
        deadline: null,
        approvalTime: null,
        approvalResult: null,
        awardUserId: null,
        awardDeptId: null,
        awardAmount: null,
        punishUserId: null,
        punishDeptId: null,
        punishAmount: null,
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
      this.ids = selection.map(item => item.dangerId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.getInspeListWithDanger()
      this.getEquipList()
      this.loadAllUsers() // 加载所有用户
      this.open = true
      this.title = '添加设备隐患治理'
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const dangerId = row.dangerId || this.ids
      getDanger(dangerId).then(response => {
        this.form = response.data
        if (this.form.equipId) {
          this.form.equipId = Number(this.form.equipId)
        }
        this.getInspeListWithDanger()
        this.getEquipList()
        this.loadAllUsers() // 加载所有用户
        this.open = true
        this.title = '修改设备隐患治理'
      })
    },
    /** 获取有隐患的检查记录列表 */
    getInspeListWithDanger() {
      // 只获取有隐患(hasDanger="2")的检查记录
      // 根据字典值确定正确的筛选值，发现隐患的字典键值为2
      listInspe({ pageSize: 10000, hasDanger: '2' }).then(response => {
        this.inspeOptions = response.rows
      })
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
    /** 设备选择改变时触发 */
    handleEquipChange(value) {
      if (value) {
        // 根据设备ID查询设备信息，获取设备位号
        const selectedEquip = this.equipList.find(equip => equip.equipId === value)
        if (selectedEquip) {
          this.form.equipCode = selectedEquip.equCode
          // 同时设置设备名称
          this.form.equipName = selectedEquip.equName
        }
      } else {
        this.form.equipCode = null
        this.form.equipName = null
      }
    },
    /** 查询用户 */
    queryUsers(query) {
      this.userLoading = true
      listUser({ nickName: query || undefined, pageSize: 100 }).then(response => {
        this.userOptions = response.rows || []
        this.userLoading = false
      }).catch(() => {
        this.userLoading = false
        this.userOptions = []
      })
    },
    /** 加载所有用户 */
    loadAllUsers() {
      this.userLoading = true
      listUser({ pageSize: 100 }).then(response => {
        this.userOptions = response.rows || []
        this.userLoading = false
      }).catch(() => {
        this.userLoading = false
        this.userOptions = []
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          // 处理设备名称
          if (this.form.equipId) {
            const selectedEquip = this.equipList.find(equip => equip.equipId === this.form.equipId)
            if (selectedEquip) {
              this.form.equipName = selectedEquip.equName
            }
          }

          // 处理检查单号
          if (this.form.inspeId) {
            const selectedInspe = this.inspeOptions.find(inspe => inspe.inspeId === this.form.inspeId)
            if (selectedInspe) {
              this.form.inspeNo = selectedInspe.inspeNo
            }
          }

          // 处理责任人姓名
          if (this.form.responId) {
            const selectedUser = this.userOptions.find(user => user.userId === this.form.responId)
            if (selectedUser) {
              this.form.responName = selectedUser.nickName
            }
          }

          if (this.form.dangerId != null) {
            updateDanger(this.form).then(response => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addDanger(this.form).then(response => {
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
      const dangerIds = row.dangerId || this.ids
      this.$modal.confirm('是否确认删除设备隐患治理编号为"' + dangerIds + '"的数据项？').then(function() {
        return delDanger(dangerIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess('删除成功')
      }).catch(() => {
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('inspe/danger/export', {
        ...this.queryParams
      }, `danger_${new Date().getTime()}.xlsx`)
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
      this.form.equipCode = row.equCode
      this.equipSelectorOpen = false
    },
    /** 打开设备选择器 */
    openEquipSelector() {
      this.equipSelectorOpen = true
      this.resetEquipQuery()
    }
  }
}
</script>
