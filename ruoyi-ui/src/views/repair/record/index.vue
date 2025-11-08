<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="检修类型" prop="sourceType">
        <el-select v-model="queryParams.sourceType" placeholder="请选择检修类型" clearable>
          <el-option
            v-for="dict in dict.type.equip_source_type"
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
        <el-select v-model="queryParams.equipLevels" placeholder="请选择管理等级" clearable>
          <el-option
            v-for="dict in dict.type.equip_levels"
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
          v-hasPermi="['repair:record:add']"
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
          v-hasPermi="['repair:record:edit']"
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
          v-hasPermi="['repair:record:remove']"
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
          v-hasPermi="['repair:record:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="recordList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="主键ID" align="center" v-if="columns[0].visible" prop="recordId"/>
      <el-table-column label="维修单号" :show-overflow-tooltip="true" align="center" v-if="columns[1].visible"
                       prop="recordNo"
      />
      <el-table-column label="关联计划" :show-overflow-tooltip="true" align="center" v-if="columns[2].visible"
                       prop="planId"
      />
      <el-table-column label="计划单号" :show-overflow-tooltip="true" align="center" v-if="columns[3].visible"
                       prop="planNo"
      />
      <el-table-column label="检修类型" align="center" v-if="columns[4].visible" prop="sourceType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.equip_source_type" :value="scope.row.sourceType"/>
        </template>
      </el-table-column>
      <el-table-column label="设备ID" align="center" v-if="columns[5].visible" prop="equipId"/>
      <el-table-column label="设备名称" :show-overflow-tooltip="true" align="center" v-if="columns[6].visible"
                       prop="equipName"
      />
      <el-table-column label="设备位号" :show-overflow-tooltip="true" align="center" v-if="columns[7].visible"
                       prop="equipCode"
      />
      <el-table-column label="设备管理等级" align="center" v-if="columns[8].visible" prop="equipLevels">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.equip_levels" :value="scope.row.equipLevels"/>
        </template>
      </el-table-column>
      <el-table-column label="责任人id" :show-overflow-tooltip="true" align="center" v-if="columns[9].visible"
                       prop="responId"
      />
      <el-table-column label="责任人姓名" :show-overflow-tooltip="true" align="center" v-if="columns[10].visible"
                       prop="responName"
      />
      <el-table-column label="故障描述" :show-overflow-tooltip="true" align="center" v-if="columns[11].visible"
                       prop="faultDisc"
      />
      <el-table-column label="检修内容" :show-overflow-tooltip="true" align="center" v-if="columns[12].visible"
                       prop="repairContent"
      />
      <el-table-column label="检修时间" :show-overflow-tooltip="true" align="center" v-if="columns[13].visible"
                       prop="repairTime"
      >
        <template slot-scope="scope">
          <!--如果是数组-->
          {{ parsePlanTime(scope.row.repairTime) }}
        </template>
      </el-table-column>
      <el-table-column label="维修方案" align="center" v-if="columns[14].visible" prop="appendix" width="100">
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
      <el-table-column label="验收时间" align="center" v-if="columns[15].visible" prop="finishTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.finishTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="申请部门ID" :show-overflow-tooltip="true" align="center" v-if="columns[16].visible"
                       prop="deptId"
      />
      <el-table-column label="申请部门" :show-overflow-tooltip="true" align="center" v-if="columns[17].visible"
                       prop="deptName"
      />
      <el-table-column label="申请人ID" :show-overflow-tooltip="true" align="center" v-if="columns[18].visible"
                       prop="applyUserId"
      />
      <el-table-column label="申请人" :show-overflow-tooltip="true" align="center" v-if="columns[19].visible"
                       prop="applyUserName"
      />
      <el-table-column label="任务ID" :show-overflow-tooltip="true" align="center" v-if="columns[20].visible"
                       prop="taskId"
      />
      <el-table-column label="流程实例ID" :show-overflow-tooltip="true" align="center" v-if="columns[21].visible"
                       prop="processInstanceId"
      />
      <el-table-column label="流程自定义ID" :show-overflow-tooltip="true" align="center" v-if="columns[22].visible"
                       prop="deployId"
      />
      <el-table-column label="备注" :show-overflow-tooltip="true" align="center" v-if="columns[23].visible"
                       prop="remark"
      />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['repair:record:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleProcess(scope.row)"
            v-hasPermi="['repair:record:list']"
          >查看流程
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['repair:record:remove']"
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

    <!-- 添加或修改检维修记录对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="900px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="维修单号" prop="recordNo">
          <el-input v-model="form.recordNo" placeholder="维修单号（自动生成）" disabled/>
        </el-form-item>
        <el-form-item label="检修类型" prop="sourceType">
          <el-select v-model="form.sourceType" placeholder="请选择检修类型">
            <el-option
              v-for="dict in dict.type.equip_source_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="检修计划" prop="planId">
          <el-select v-model="form.planId" placeholder="请选择检修计划"
                     filterable
                     remote
                     reserve-keyword
                     :loading="repairPlanLoading"
                     :remote-method="remoteRepairPlan" @change="handleRepairPlanChange"
          >
            <el-option
              v-for="item in repairPlanList"
              :key="item.planId"
              :label="item.planNo"
              :value="item.planId"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <!--        <el-form-item label="设备" prop="equipId">-->
        <!--          <el-row>-->
        <!--            <el-col :span="20">-->
        <!--              <el-select-->
        <!--                v-model="form.equipId"-->
        <!--                placeholder="请选择设备"-->
        <!--                @change="handleEquipChange"-->
        <!--                style="width: 100%"-->
        <!--              >-->
        <!--                <el-option-->
        <!--                  v-for="item in equipList"-->
        <!--                  :key="item.equipId"-->
        <!--                  :label="item.equName"-->
        <!--                  :value="item.equipId"-->
        <!--                >-->
        <!--                  <span style="float: left">{{ item.equName }}</span>-->
        <!--                  <span style="float: right; color: #8492a6; font-size: 13px">{{ item.equCode }}</span>-->
        <!--                </el-option>-->
        <!--              </el-select>-->
        <!--            </el-col>-->
        <!--            <el-col :span="4">-->
        <!--              <el-button type="primary" @click="openEquipSelector" style="margin-left: 10px;">选择设备</el-button>-->
        <!--            </el-col>-->
        <!--          </el-row>-->
        <!--        </el-form-item>-->
        <el-form-item label="设备" prop="equipName">
          <el-input v-model="form.equipName" placeholder="设备" disabled/>
        </el-form-item>
        <el-form-item label="设备位号" prop="equipCode">
          <el-input v-model="form.equipCode" placeholder="设备位号" disabled/>
        </el-form-item>
        <el-form-item label="管理等级" prop="equipLevels">
          <el-select v-model="form.equipLevels" placeholder="请选择管理等级" disabled>
            <el-option
              v-for="dict in dict.type.equip_levels"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="故障描述" prop="faultDisc">
          <el-input v-model="form.faultDisc" type="textarea" placeholder="请输入内容"/>
        </el-form-item>
        <el-form-item label="检修内容" prop="repairContent">
          <el-input v-model="form.repairContent" type="textarea" placeholder="请输入内容"/>
        </el-form-item>
        <el-form-item label="检修时间" prop="planTime">
          <el-date-picker
            v-model="form.planTime"
            type="daterange"
            placeholder="选择计划时间"
            value-format="yyyy-MM-dd"
            style="width: 100%"
          />
        </el-form-item>
        <el-form-item label="维修方案" prop="appendix">
          <file-upload v-model="form.appendix"/>
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
        <el-divider content-position="center">设备维修明细信息</el-divider>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAddEquipRepairDetail">添加
            </el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" icon="el-icon-delete" size="mini" @click="handleDeleteEquipRepairDetail">删除
            </el-button>
          </el-col>
        </el-row>
        <el-table :data="equipRepairDetailList" :row-class-name="rowEquipRepairDetailIndex"
                  @selection-change="handleEquipRepairDetailSelectionChange" ref="equipRepairDetail"
        >
          <el-table-column type="selection" width="50" align="center"/>
          <el-table-column label="序号" align="center" prop="index" width="50"/>
          <el-table-column label="执行人" prop="assignedName" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.assignedName" placeholder="请输入执行人"/>
            </template>
          </el-table-column>
          <el-table-column label="开始时间" prop="startTime" width="240">
            <template slot-scope="scope">
              <el-date-picker clearable v-model="scope.row.startTime" type="date" value-format="yyyy-MM-dd"
                              placeholder="请选择开始时间"
              />
            </template>
          </el-table-column>
          <el-table-column label="完成时间" prop="finishTime" width="240">
            <template slot-scope="scope">
              <el-date-picker clearable v-model="scope.row.finishTime" type="date" value-format="yyyy-MM-dd"
                              placeholder="请选择完成时间"
              />
            </template>
          </el-table-column>
          <el-table-column label="工时" prop="menHours" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.menHours" placeholder="请输入工时"/>
            </template>
          </el-table-column>
          <el-table-column label="检修结果" prop="checkResult" width="150">
            <template slot-scope="scope">
              <el-select v-model="scope.row.checkResult" placeholder="请选择检修结果">
                <el-option
                  v-for="dict in dict.type.equip_fun"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="检修意见" prop="checkOpinion" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.checkOpinion" placeholder="请输入检修意见"/>
            </template>
          </el-table-column>
        </el-table>
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
import { listRecord, getRecord, delRecord, addRecord, updateRecord } from '@/api/repair/record'
import { listLedger } from '@/api/equip/ledger'
import TaskDetail from '@/components/TaskDetail/index.vue'
import { listRepairPlan } from '@/api/repair/plan'

export default {
  name: 'Record',
  components: { TaskDetail },
  dicts: ['equip_levels', 'equip_user', 'equip_repair_sratus', 'equip_fun', 'equip_source_type', 'equip_ledger', 'sys_process_category', 'equip_type', 'equip_location'],
  data() {
    return {
      baseUrl: process.env.VUE_APP_BASE_API,
      //表格展示列
      columns: [
        { key: 0, label: '主键ID', visible: true },
        { key: 1, label: '维修单号', visible: true },
        { key: 2, label: '关联计划', visible: false },
        { key: 3, label: '计划单号', visible: true },
        { key: 4, label: '检修类型', visible: true },
        { key: 5, label: '设备ID', visible: false },
        { key: 6, label: '设备名称', visible: true },
        { key: 7, label: '设备位号', visible: true },
        { key: 8, label: '设备管理等级', visible: true },
        { key: 9, label: '责任人id', visible: false },
        { key: 10, label: '责任人姓名', visible: false },
        { key: 11, label: '故障描述', visible: true },
        { key: 12, label: '检修内容', visible: true },
        { key: 13, label: '检修时间', visible: true },
        { key: 14, label: '维修方案', visible: true },
        { key: 15, label: '验收时间', visible: false },
        { key: 16, label: '申请部门ID', visible: false },
        { key: 17, label: '申请部门', visible: false },
        { key: 18, label: '申请人ID', visible: false },
        { key: 19, label: '申请人', visible: false },
        { key: 20, label: '任务ID', visible: false },
        { key: 21, label: '流程实例ID', visible: false },
        { key: 22, label: '流程自定义ID', visible: false },
        { key: 23, label: '备注', visible: false }
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
      checkedEquipRepairDetail: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 检维修记录表格数据
      recordList: [],
      // 设备维修明细表格数据
      equipRepairDetailList: [],
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
        sourceType: null,
        equipName: null,
        equipLevels: null
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
      //维修计划
      repairPlanList: [],
      repairPlanLoading: false,
      repairPlanQuery: {
        pageNum: 1,
        pageSize: 100
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        sourceType: [
          { required: true, message: '检修类型不能为空', trigger: 'change' }
        ],
        equipId: [
          { required: true, message: '设备不能为空', trigger: 'change' }
        ],
        faultDisc: [
          { required: true, message: '故障描述不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  created() {
    this.getList()
    this.getEquipList()
    this.getRepairPlanList()
  },
  methods: {
    /** 查询维修计划列表 */
    getRepairPlanList() {
      this.repairPlanLoading = true
      this.repairPlanList = []
      listRepairPlan(this.repairPlanQuery).then(response => {
        this.repairPlanLoading = false
        this.repairPlanList = response.rows
      })
    },
    remoteRepairPlan(query) {
      this.repairPlanLoading = true
      this.repairPlanList = []
      listRepairPlan({
        pageNum: 1,
        pageSize: 100,
        planNo: query
      }).then(response => {
        this.repairPlanList = response.rows
        this.repairPlanLoading = false

      })
    },
    handleRepairPlanChange(planId) {
      const selectedItem = this.repairPlanList.find(item => item.planId === planId)
      console.log(selectedItem) // 获取到完整的 item 对象
      if (selectedItem) {
        this.form.planNo = selectedItem.planNo
        this.form.equipId = selectedItem.equipId
        this.form.equipName = selectedItem.equipName
        this.form.equipLevels = selectedItem.equipLevels
        this.form.equipCode = selectedItem.equipCode
      }
    },
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
    /** 查询检维修记录列表 */
    getList() {
      this.loading = true
      listRecord(this.queryParams).then(response => {
        this.recordList = response.rows
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
        recordId: null,
        recordNo: null,
        planId: null,
        planNo: null,
        sourceType: null,
        equipId: null,
        equipName: null,
        equipCode: null,
        equipLevels: null,
        responId: null,
        responName: null,
        faultDisc: null,
        repairContent: null,
        repairTime: null,
        appendix: null,
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
      this.equipRepairDetailList = []
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
      this.title = '添加检维修记录'
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const recordId = row.recordId || this.ids
      getRecord(recordId).then(response => {
        this.form = response.data
        if (this.form.equipId != undefined && this.form.equipId != '') {
          this.form.equipId = Number(this.form.equipId)
        }
        this.equipRepairDetailList = response.data.equipRepairDetailList
        this.open = true
        this.title = '修改检维修记录'
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          this.form.equipRepairDetailList = this.equipRepairDetailList
          if (this.form.recordId != null) {
            updateRecord(this.form).then(response => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addRecord(this.form).then(response => {
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
      const recordIds = row.recordId || this.ids
      this.$modal.confirm('是否确认删除检维修记录编号为"' + recordIds + '"的数据项？').then(function() {
        return delRecord(recordIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess('删除成功')
      }).catch(() => {
      })
    },
    /** 设备维修明细序号 */
    rowEquipRepairDetailIndex({ row, rowIndex }) {
      row.index = rowIndex + 1
    },
    /** 设备维修明细添加按钮操作 */
    handleAddEquipRepairDetail() {
      let obj = {}
      obj.assignedName = ''
      obj.startTime = ''
      obj.finishTime = ''
      obj.menHours = ''
      obj.repairContent = ''
      obj.repairResult = ''
      obj.usedMaterials = ''
      obj.checkResult = ''
      obj.checkOpinion = ''
      this.equipRepairDetailList.push(obj)
    },
    /** 设备维修明细删除按钮操作 */
    handleDeleteEquipRepairDetail() {
      if (this.checkedEquipRepairDetail.length == 0) {
        this.$modal.msgError('请先选择要删除的设备维修明细数据')
      } else {
        const equipRepairDetailList = this.equipRepairDetailList
        const checkedEquipRepairDetail = this.checkedEquipRepairDetail
        this.equipRepairDetailList = equipRepairDetailList.filter(function(item) {
          return checkedEquipRepairDetail.indexOf(item.index) == -1
        })
      }
    },
    /** 复选框选中数据 */
    handleEquipRepairDetailSelectionChange(selection) {
      this.checkedEquipRepairDetail = selection.map(item => item.index)
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('repair/record/export', {
        ...this.queryParams
      }, `record_${new Date().getTime()}.xlsx`)
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
