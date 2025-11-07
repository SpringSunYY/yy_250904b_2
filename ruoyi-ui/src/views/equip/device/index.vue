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
          v-hasPermi="['equip:device:add']"
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
          v-hasPermi="['equip:device:edit']"
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
          v-hasPermi="['equip:device:remove']"
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
          v-hasPermi="['equip:device:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="deviceList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="序号" align="center" v-if="columns[0].visible" prop="deviceId"/>
      <el-table-column label="验收单号" :show-overflow-tooltip="true" align="center" v-if="columns[1].visible"
                       prop="deviceNo"
      />
      <el-table-column label="订单编号" :show-overflow-tooltip="true" align="center" v-if="columns[2].visible"
                       prop="orderId"
      />
      <el-table-column label="订单编号" :show-overflow-tooltip="true" align="center" v-if="columns[3].visible"
                       prop="orderNo"
      />
      <el-table-column label="设备名称" :show-overflow-tooltip="true" align="center" v-if="columns[4].visible"
                       prop="equipName"
      />
      <el-table-column label="设备位号" :show-overflow-tooltip="true" align="center" v-if="columns[5].visible"
                       prop="equipNo"
      />
      <el-table-column label="设备型号" :show-overflow-tooltip="true" align="center" v-if="columns[6].visible"
                       prop="equipModel"
      />
      <el-table-column label="入厂日期" align="center" v-if="columns[7].visible" prop="inDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.inDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="安装单位" :show-overflow-tooltip="true" align="center" v-if="columns[8].visible"
                       prop="instUnit"
      />
      <el-table-column label="安装负责人" :show-overflow-tooltip="true" align="center" v-if="columns[9].visible"
                       prop="instPerson"
      />
      <el-table-column label="联系电话" :show-overflow-tooltip="true" align="center" v-if="columns[10].visible"
                       prop="instPhone"
      />
      <el-table-column label="安装日期" align="center" v-if="columns[11].visible" prop="instDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.instDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="安装位置" align="center" v-if="columns[12].visible" prop="instLocation">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.equip_location" :value="scope.row.instLocation"/>
        </template>
      </el-table-column>
      <el-table-column label="安装调试单位" :show-overflow-tooltip="true" align="center" v-if="columns[13].visible"
                       prop="instAdjustUnit"
      />
      <el-table-column label="相关附件" align="center" v-if="columns[14].visible" prop="appendix" width="100">
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
      <el-table-column label="验收结果" align="center" v-if="columns[15].visible" prop="acceptanceResult">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.equip_fun" :value="scope.row.acceptanceResult"/>
        </template>
      </el-table-column>
      <el-table-column label="验收意见" :show-overflow-tooltip="true" align="center" v-if="columns[16].visible"
                       prop="acceptanceOpinion"
      />
      <el-table-column label="申请部门ID" :show-overflow-tooltip="true" align="center" v-if="columns[17].visible"
                       prop="deptId"
      />
      <el-table-column label="申请部门" :show-overflow-tooltip="true" align="center" v-if="columns[18].visible"
                       prop="deptName"
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
      <el-table-column label="流程实例ID" :show-overflow-tooltip="true" align="center" v-if="columns[22].visible"
                       prop="processInstanceId"
      />
      <el-table-column label="流程自定义ID" :show-overflow-tooltip="true" align="center" v-if="columns[23].visible"
                       prop="deployId"
      />
      <el-table-column label="备注" :show-overflow-tooltip="true" align="center" v-if="columns[24].visible"
                       prop="remark"
      />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" fixed="right" width="200">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['equip:device:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleProcess(scope.row)"
            v-hasPermi="['equip:device:list']"
          >查看流程
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['equip:device:remove']"
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

    <!-- 添加或修改设备安装验收对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="验收单号" prop="deviceNo" v-if="form.deviceId">
          <el-input v-model="form.deviceNo" placeholder="请输入验收单号" readonly/>
        </el-form-item>
        <el-form-item label="订单" prop="orderId">
          <el-select
            v-model="form.orderId"
            placeholder="请选择订单"
            @change="handleOrderChange"
            style="width: 100%"
          >
            <el-option
              v-for="item in orderList"
              :key="item.orderId"
              :label="item.orderNo"
              :value="item.orderId"
            >
              <span style="float: left">{{ item.equipName }}</span>
              <span style="float: right; color: #8492a6; font-size: 13px">{{ item.orderNo }}</span>
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="设备名称" prop="equipName">
          <el-input v-model="form.equipName" placeholder="请输入设备名称"/>
        </el-form-item>
        <el-form-item label="设备位号" prop="equipNo">
          <el-input v-model="form.equipNo" placeholder="请输入设备位号"/>
        </el-form-item>
        <el-form-item label="设备型号" prop="equipModel">
          <el-input v-model="form.equipModel" placeholder="请输入设备型号"/>
        </el-form-item>
        <el-form-item label="入厂日期" prop="inDate">
          <el-date-picker clearable
                          v-model="form.inDate"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择入厂日期"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="安装单位" prop="instUnit">
          <el-input v-model="form.instUnit" placeholder="请输入安装单位"/>
        </el-form-item>
        <el-form-item label="安装负责人" prop="instPerson">
          <el-input v-model="form.instPerson" placeholder="请输入安装负责人"/>
        </el-form-item>
        <el-form-item label="联系电话" prop="instPhone">
          <el-input v-model="form.instPhone" placeholder="请输入联系电话"/>
        </el-form-item>
        <el-form-item label="安装日期" prop="instDate">
          <el-date-picker clearable
                          v-model="form.instDate"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择安装日期"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="安装位置" prop="instLocation">
          <el-select v-model="form.instLocation" placeholder="请选择安装位置">
            <el-option
              v-for="dict in dict.type.equip_location"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="安装调试单位" prop="instAdjustUnit">
          <el-input v-model="form.instAdjustUnit" placeholder="请输入安装调试单位"/>
        </el-form-item>
        <el-form-item label="相关附件" prop="appendix">
          <file-upload v-model="form.appendix"/>
        </el-form-item>
        <el-form-item label="验收结果" prop="acceptanceResult">
          <el-select v-model="form.acceptanceResult" placeholder="请选择验收结果">
            <el-option
              v-for="dict in dict.type.equip_fun"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="验收意见" prop="acceptanceOpinion">
          <el-input v-model="form.acceptanceOpinion" placeholder="请输入验收意见"/>
        </el-form-item>
        <el-divider content-position="center">安装验收明细信息</el-divider>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAddEquipDeviceItem">添加</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" icon="el-icon-delete" size="mini" @click="handleDeleteEquipDeviceItem">删除
            </el-button>
          </el-col>
        </el-row>
        <el-table :data="equipDeviceItemList" :row-class-name="rowEquipDeviceItemIndex"
                  @selection-change="handleEquipDeviceItemSelectionChange" ref="equipDeviceItem"
        >
          <el-table-column type="selection" width="50" align="center"/>
          <el-table-column label="序号" align="center" prop="index" width="50"/>
          <el-table-column label="外观检查" prop="vi" width="150">
            <template slot-scope="scope">
              <el-select v-model="scope.row.vi" placeholder="请选择外观检查">
                <el-option
                  v-for="dict in dict.type.equip_vi"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="功能测试" prop="funTest" width="150">
            <template slot-scope="scope">
              <el-select v-model="scope.row.funTest" placeholder="请选择功能测试">
                <el-option
                  v-for="dict in dict.type.equip_fun"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="技术资料" prop="tecPaper" width="150">
            <template slot-scope="scope">
              <el-select v-model="scope.row.tecPaper" placeholder="请选择技术资料">
                <el-option
                  v-for="dict in dict.type.equip_paper"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </template>
          </el-table-column>
        </el-table>
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
import { addDevice, delDevice, getDevice, listDevice, updateDevice } from '@/api/equip/device'
import FlowRecordList from '@/components/FlowRecordList/index.vue'
import TaskDetail from '@/components/TaskDetail/index.vue'
import { listLedger } from '@/api/equip/ledger'
import { listOrder } from '@/api/pur/order'

export default {
  name: 'Device',
  components: { TaskDetail, FlowRecordList },
  dicts: ['equip_fun', 'equip_repair_sratus', 'equip_levels', 'equip_type', 'equip_vi', 'equip_location', 'equip_paper', 'sys_process_category'],
  data() {
    return {
      baseUrl: process.env.VUE_APP_BASE_API,
      //表格展示列
      columns: [
        { key: 0, label: '序号', visible: true },
        { key: 1, label: '验收单号', visible: true },
        { key: 2, label: '订单', visible: false },
        { key: 3, label: '订单编号', visible: true },
        { key: 4, label: '设备名称', visible: true },
        { key: 5, label: '设备位号', visible: true },
        { key: 6, label: '设备型号', visible: true },
        { key: 7, label: '入厂日期', visible: true },
        { key: 8, label: '安装单位', visible: true },
        { key: 9, label: '安装负责人', visible: true },
        { key: 10, label: '联系电话', visible: true },
        { key: 11, label: '安装日期', visible: true },
        { key: 12, label: '安装位置', visible: true },
        { key: 13, label: '安装调试单位', visible: true },
        { key: 14, label: '相关附件', visible: false },
        { key: 15, label: '验收结果', visible: true },
        { key: 16, label: '验收意见', visible: false },
        { key: 17, label: '申请部门ID', visible: false },
        { key: 18, label: '申请部门', visible: false },
        { key: 19, label: '申请人ID', visible: false },
        { key: 20, label: '申请人', visible: false },
        { key: 21, label: '流程实例ID', visible: false },
        { key: 22, label: '流程自定义ID', visible: false },
        { key: 23, label: '备注', visible: false },
        { key: 24, label: '更新时间', visible: false }
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
      // 设备安装验收表格数据
      deviceList: [],
      // 安装验收明细表格数据
      equipDeviceItemList: [],
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        equipName: null,
        status: null
      },
      // 设备查询参数
      orderQueryParams: {
        pageNum: 1,
        pageSize: 1000,
        equName: undefined,
        equCode: undefined
      },
      // 订单列表
      orderList: [],
      // 设备加载
      orderLoading: false,
      // 表单参数
      form: {},
      // 表单校验
      rules: {}
    }
  },
  created() {
    this.getList()
    this.getOrderList()
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
    /** 查询设备安装验收列表 */
    getList() {
      this.loading = true
      listDevice(this.queryParams).then(response => {
        this.deviceList = response.rows
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
        deviceId: null,
        deviceNo: null,
        equipName: null,
        equipNo: null,
        equipModel: null,
        inDate: null,
        instUnit: null,
        instPerson: null,
        instPhone: null,
        instDate: null,
        instLocation: null,
        instAdjustUnit: null,
        appendix: null,
        acceptanceResult: null,
        acceptanceOpinion: null,
        deptId: null,
        deptName: null,
        applyUserId: null,
        applyUserName: null,
        processInstanceId: null,
        deployId: null,
        remark: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        upDateTime: null
      }
      this.equipDeviceItemList = []
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
      this.ids = selection.map(item => item.deviceId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = '添加设备安装验收'
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const deviceId = row.deviceId || this.ids
      getDevice(deviceId).then(response => {
        this.form = response.data
        this.equipDeviceItemList = response.data.equipDeviceItemList
        this.open = true
        this.title = '修改设备安装验收'
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          this.form.equipDeviceItemList = this.equipDeviceItemList
          if (this.form.deviceId != null) {
            updateDevice(this.form).then(response => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addDevice(this.form).then(response => {
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
      const deviceIds = row.deviceId || this.ids
      this.$modal.confirm('是否确认删除设备安装验收编号为"' + deviceIds + '"的数据项？').then(function() {
        return delDevice(deviceIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess('删除成功')
      }).catch(() => {
      })
    },
    /** 安装验收明细序号 */
    rowEquipDeviceItemIndex({ row, rowIndex }) {
      row.index = rowIndex + 1
    },
    /** 安装验收明细添加按钮操作 */
    handleAddEquipDeviceItem() {
      let obj = {}
      obj.vi = ''
      obj.funTest = ''
      obj.tecPaper = ''
      obj.attachment = ''
      this.equipDeviceItemList.push(obj)
    },
    /** 安装验收明细删除按钮操作 */
    handleDeleteEquipDeviceItem() {
      if (this.checkedEquipDeviceItem.length == 0) {
        this.$modal.msgError('请先选择要删除的安装验收明细数据')
      } else {
        const equipDeviceItemList = this.equipDeviceItemList
        const checkedEquipDeviceItem = this.checkedEquipDeviceItem
        this.equipDeviceItemList = equipDeviceItemList.filter(function(item) {
          return checkedEquipDeviceItem.indexOf(item.index) == -1
        })
      }
    },
    /** 复选框选中数据 */
    handleEquipDeviceItemSelectionChange(selection) {
      this.checkedEquipDeviceItem = selection.map(item => item.index)
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('equip/device/export', {
        ...this.queryParams
      }, `device_${new Date().getTime()}.xlsx`)
    },
    /** 设备选择变化处理 */
    handleOrderChange(val) {
      // 从设备台账列表中查找选中的设备
      const selectedOrder = this.orderList.find(item =>
        item.orderId === val)
      if (selectedOrder) {
        // 设置设备名称和设备位号
        this.form.equipName = selectedOrder.equipName
      } else {
        this.form.deviceName = ''
      }
    },
    /** 获取设备列表 */
    getOrderList() {
      this.orderLoading = true
      listOrder(this.orderQueryParams).then(response => {
        this.orderLoading = false
        this.orderList = response.rows
      })
    },
    /** 设备搜索 */
    handleOrderQuery() {
      this.orderQueryParams.pageNum = 1
      this.getOrderList()
    },
    /** 重置设备搜索 */
    resetEquipQuery() {
      this.resetForm('equipQueryForm')
      this.handleOrderQuery()
    },
    /** 选择设备 */
    handleSelectEquip(row) {
      this.form.equipId = row.equipId
      this.form.equipName = row.equName
      this.form.equipNo = row.equCode
      this.form.equipModel = row.specification
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
