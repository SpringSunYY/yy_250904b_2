<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="计划类型" prop="planType">
        <el-select v-model="queryParams.planType" placeholder="请选择计划类型" clearable>
          <el-option
            v-for="dict in dict.type.equip_repair_plan"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="设备名称" prop="equipName">
        <el-input
          v-model="queryParams.equipName"
          placeholder="请输入设备名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="管理等级" prop="equipLevels">
        <el-input
          v-model="queryParams.equipLevels"
          placeholder="请输入设备管理等级"
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
          v-hasPermi="['repair:plan:add']"
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
          v-hasPermi="['repair:plan:edit']"
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
          v-hasPermi="['repair:plan:remove']"
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
          v-hasPermi="['repair:plan:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="planList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="序号" align="center" v-if="columns[0].visible" prop="planId"/>
      <el-table-column label="计划编号" :show-overflow-tooltip="true" align="center" v-if="columns[1].visible"
                       prop="planNo"
      />
      <el-table-column label="计划类型" align="center" v-if="columns[2].visible" prop="planType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.equip_repair_plan" :value="scope.row.planType"/>
        </template>
      </el-table-column>
      <el-table-column label="设备ID" align="center" v-if="columns[3].visible" prop="equipId">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.equip_ledger" :value="scope.row.equipId"/>
        </template>
      </el-table-column>
      <el-table-column label="设备名称" :show-overflow-tooltip="true" align="center" v-if="columns[4].visible"
                       prop="equipName"
      />
      <el-table-column label="设备位号" :show-overflow-tooltip="true" align="center" v-if="columns[5].visible"
                       prop="equipCode"
      />
      <el-table-column label="设备管理等级" :show-overflow-tooltip="true" align="center" v-if="columns[6].visible"
                       prop="equipLevels"
      >
        <template slot-scope="scope">
          <dict-tag :options="dict.type.equip_levels" :value="scope.row.equipLevels"/>
        </template>
      </el-table-column>
      <el-table-column label="检修内容" :show-overflow-tooltip="true" align="center" v-if="columns[7].visible"
                       prop="planContent"
      >
        <template slot-scope="scope">
          <div v-html="removePTags(scope.row.planContent)"></div>
        </template>
      </el-table-column>
      <el-table-column label="检修时间" :show-overflow-tooltip="true" align="center" v-if="columns[8].visible"
                       prop="planTime"
      >
        <template slot-scope="scope">
          <!--如果是数组-->
          {{ parsePlanTime(scope.row.planTime) }}
        </template>
      </el-table-column>
      <el-table-column label="检修费用" :show-overflow-tooltip="true" align="center" v-if="columns[9].visible"
                       prop="budget"
      />
      <el-table-column label="检维修方案" align="center" v-if="columns[10].visible" prop="appendix" width="100">
        <template slot-scope="scope">
          <div v-if="scope.row.appendix">
            <el-tooltip placement="top">
              <div slot="content">
                <div v-for="(file,index) in scope.row.appendix.split(',')"
                     :key="index"
                     style="text-align: left;padding: 5px;"
                >
                  <el-link
                    :download="getFileName(file)"
                    :href="baseUrl+file"
                    :underline="false"
                    target="_blank"
                    style="color: white;font-size: 12px"
                  >
                    <span style="cursor: pointer;"> {{ getFileName(file) }} </span>
                  </el-link>
                </div>
              </div>
              <span style="cursor: pointer; color: #409EFF;">查看文件</span>
            </el-tooltip>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="计划年度" :show-overflow-tooltip="true" align="center" v-if="columns[11].visible"
                       prop="planYear"
      />
      <el-table-column label="计划月份" :show-overflow-tooltip="true" align="center" v-if="columns[12].visible"
                       prop="planMonth"
      />
      <el-table-column label="申请部门ID" :show-overflow-tooltip="true" align="center" v-if="columns[13].visible"
                       prop="deptId"
      />
      <el-table-column label="申请部门" :show-overflow-tooltip="true" align="center" v-if="columns[14].visible"
                       prop="deptName"
      />
      <el-table-column label="申请人ID" :show-overflow-tooltip="true" align="center" v-if="columns[15].visible"
                       prop="applyUserId"
      />
      <el-table-column label="申请人" :show-overflow-tooltip="true" align="center" v-if="columns[16].visible"
                       prop="applyUserName"
      />
      <el-table-column label="任务ID" :show-overflow-tooltip="true" align="center" v-if="columns[17].visible"
                       prop="taskId"
      />
      <el-table-column label="流程实例ID" :show-overflow-tooltip="true" align="center" v-if="columns[18].visible"
                       prop="processInstanceId"
      />
      <el-table-column label="流程自定义ID" :show-overflow-tooltip="true" align="center" v-if="columns[19].visible"
                       prop="deployId"
      />
      <el-table-column label="备注" :show-overflow-tooltip="true" align="center" v-if="columns[20].visible"
                       prop="remark"
      />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['repair:plan:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleProcess(scope.row)"
            v-hasPermi="['repair:plan:list']"
          >查看流程
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['repair:plan:remove']"
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

    <!-- 添加或修改设备检修计划对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="900px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="计划类型" prop="planType">
          <el-select v-model="form.planType" placeholder="请选择计划类型">
            <el-option
              v-for="dict in dict.type.equip_repair_plan"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
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
        <el-form-item label="检修内容">
          <editor v-model="form.planContent" :min-height="192"/>
        </el-form-item>
        <!--        <el-form-item label="检修时间" prop="planTime">-->
        <!--          <el-date-picker-->
        <!--            v-model="form.planTime"-->
        <!--            type="daterange"-->
        <!--            placeholder="选择计划时间"-->
        <!--            value-format="yyyy-MM-dd"-->
        <!--            style="width: 100%"-->
        <!--          />-->
        <!--        </el-form-item>-->
        <el-form-item label="预算费用" prop="budget">
          <el-input-number :min="0" :precision="2" v-model="form.budget" placeholder="请输入预算费用"/>
        </el-form-item>
        <el-form-item label="检维修方案" prop="appendix">
          <file-upload v-model="form.appendix"/>
        </el-form-item>
        <el-form-item label="计划年度" prop="planYear">
          <el-select v-model="form.planYear" placeholder="请选择计划年度">
            <el-option
              v-for="year in planYearOptions"
              :key="year"
              :label="year"
              :value="year"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="计划月份" prop="planMonth">
          <el-select v-model="form.planMonth" placeholder="请选择计划月份">
            <el-option
              v-for="month in planMonthOptions"
              :key="month.value"
              :label="month.label"
              :value="month.value"
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
import {addPlan, delPlan, getPlan, listRepairPlan, updatePlan} from '@/api/repair/plan'
import {listLedger} from '@/api/equip/ledger'
import TaskDetail from '@/components/TaskDetail/index.vue'

export default {
  name: 'Plan',
  components: { TaskDetail },
  dicts: ['equip_repair_sratus', 'equip_repair_plan', 'equip_ledger', 'sys_process_category', 'equip_levels', 'equip_type', 'equip_location'],
  data() {
    return {
      baseUrl: process.env.VUE_APP_BASE_API,
      //表格展示列
      columns: [
        { key: 0, label: '序号', visible: true },
        { key: 1, label: '计划编号', visible: true },
        { key: 2, label: '计划类型', visible: true },
        { key: 3, label: '设备ID', visible: false },
        { key: 4, label: '设备名称', visible: true },
        { key: 5, label: '设备位号', visible: true },
        { key: 6, label: '设备管理等级', visible: true },
        { key: 7, label: '检修内容', visible: true },
        { key: 8, label: '检修时间', visible: true },
        { key: 9, label: '检修费用', visible: true },
        { key: 10, label: '检维修方案', visible: true },
        { key: 11, label: '计划年度', visible: true },
        { key: 12, label: '计划月份', visible: true },
        { key: 13, label: '申请部门ID', visible: false },
        { key: 14, label: '申请部门', visible: false },
        { key: 15, label: '申请人ID', visible: false },
        { key: 16, label: '申请人', visible: false },
        { key: 17, label: '任务ID', visible: false },
        { key: 18, label: '流程实例ID', visible: false },
        { key: 19, label: '流程自定义ID', visible: false },
        { key: 20, label: '备注', visible: false }
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
      // 设备检修计划表格数据
      planList: [],
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
        planType: null,
        equipName: null,
        equipLevels: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        planType: [
          { required: true, message: '计划类型不能为空', trigger: 'change' }
        ],
        equipId: [
          { required: true, message: '设备不能为空', trigger: 'change' }
        ]
      },

      // 设备列表
      equipList: [],
      // 设备选择器列表
      equipListSelector: [],
      // 设备总数
      equipTotal: 0,
      // 设备加载状态
      equipLoading: false,
      // 设备查询参数
      equipQueryParams: {
        pageNum: 1,
        pageSize: 10,
        equName: undefined,
        equCode: undefined
      },
      // 防抖定时器
      equipSearchTimer: null,
      // 计划年度选项
      planYearOptions: [],
      // 计划月份选项
      planMonthOptions: [
        { value: '01', label: '1月' },
        { value: '02', label: '2月' },
        { value: '03', label: '3月' },
        { value: '04', label: '4月' },
        { value: '05', label: '5月' },
        { value: '06', label: '6月' },
        { value: '07', label: '7月' },
        { value: '08', label: '8月' },
        { value: '09', label: '9月' },
        { value: '10', label: '10月' },
        { value: '11', label: '11月' },
        { value: '12', label: '12月' }
      ]
    }
  },
  created() {
    this.getList()
    this.getEquipList()
    this.generatePlanYearOptions()
  },
  methods: {
    //获取文件名 此功能只可以下载只有一个文件的
    getFileName(filePath) {
      if (filePath == null) {
        return
      }
      // 提取文件名或根据需求生成文件名
      return filePath.substring(filePath.lastIndexOf('/') + 1)
    },
    //格式化时间
    parsePlanTime(time) {
      if (time === undefined || time === null || time === '') {
        return ''
      }
      //如果传过来的是：'["2025-10-22","2025-11-24"]'
      if (time.startsWith('[') && time.endsWith(']')) {
        const dateRange = JSON.parse(time)
        return dateRange[0] + '至' + dateRange[1]
      }
      return time
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
    /** 查询设备检修计划列表 */
    getList() {
      this.loading = true
      listRepairPlan(this.queryParams).then(response => {
        this.planList = response.rows
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
        planId: null,
        planNo: null,
        planType: null,
        equipId: null,
        equipName: null,
        equipCode: null,
        equipLevels: null,
        planContent: null,
        planTime: null,
        budget: null,
        appendix: null,
        planYear: null,
        planMonth: null,
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
      this.ids = selection.map(item => item.planId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = '添加设备检修计划'
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const planId = row.planId || this.ids
      getPlan(planId).then(response => {
        this.form = response.data
        if (this.form.equipId != undefined && this.form.equipId != '') {
          this.form.equipId = Number(this.form.equipId)
        }
        this.open = true
        this.title = '修改设备检修计划'
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          // 自动生成计划编号
          if (this.form.planId == null) {
            this.generatePlanNo()
          }

          if (this.form.planId != null) {
            updatePlan(this.form).then(response => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addPlan(this.form).then(response => {
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
      const planIds = row.planId || this.ids
      this.$modal.confirm('是否确认删除设备检修计划编号为"' + planIds + '"的数据项？').then(function() {
        return delPlan(planIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess('删除成功')
      }).catch(() => {
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('repair/plan/export', {
        ...this.queryParams
      }, `plan_${new Date().getTime()}.xlsx`)
    },
    /** 生成计划编号 */
    generatePlanNo() {
      // 生成计划编号格式：类型代码+年份+月份+三位序号
      // 例如：NDJH202510001（NDJH表示年度计划，2025表示年份，10表示月份，001表示序号）
      const planTypeMap = {
        '1': 'NDJH',  // 年度计划
        '2': 'YDJH',  // 月度计划
        '3': 'ZBJH'   // 周边计划
      }

      const typeCode = planTypeMap[this.form.planType] || 'JH'
      const year = this.form.planYear || new Date().getFullYear()
      const month = this.form.planMonth || (new Date().getMonth() + 1).toString().padStart(2, '0')

      // 使用时间戳作为序号的一部分，确保唯一性
      const timestamp = Date.now().toString().slice(-5)

      this.form.planNo = `${typeCode}${year}${month}${timestamp}`
    },
    /** 生成计划年度选项 */
    generatePlanYearOptions() {
      const currentYear = new Date().getFullYear()
      for (let year = currentYear - 3; year <= currentYear + 20; year++) {
        this.planYearOptions.push(year.toString())
      }
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
    /** 设备选择变更 */
    handleEquipChange(val) {
      const selectedEquip = this.equipList.find(item => item.equipId === val)
      if (selectedEquip) {
        this.form.equipName = selectedEquip.equName
        this.form.equipLevels = selectedEquip.equipLevels
        this.form.equipCode = selectedEquip.equCode
      }
    },
    /** 去除内容前后的<p>标签 */
    removePTags(content) {
      if (!content) return ''
      return content.replace(/^<p>/, '').replace(/<\/p>$/, '')
    },
    /** 格式化月份显示 */
    formatMonth(month) {
      if (!month) return ''
      // 将"01"转换为"1月"，"05"转换为"5月"等
      const monthMap = {
        '01': '1月',
        '02': '2月',
        '03': '3月',
        '04': '4月',
        '05': '5月',
        '06': '6月',
        '07': '7月',
        '08': '8月',
        '09': '9月',
        '10': '10月',
        '11': '11月',
        '12': '12月'
      }
      return monthMap[month] || month
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
      this.form.equipLevels = row.equipLevels
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
