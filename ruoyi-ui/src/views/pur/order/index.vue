<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="供应商" prop="supplierName">
        <el-input
          v-model="queryParams.supplierName"
          placeholder="请输入供应商"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="申请部门" prop="deptName">
        <el-input
          v-model="queryParams.deptName"
          placeholder="请输入申请部门"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="申请人" prop="applyUserName">
        <el-input
          v-model="queryParams.applyUserName"
          placeholder="请输入申请人"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="orderStatus">
        <el-select v-model="queryParams.orderStatus" placeholder="请选择状态" clearable>
          <el-option
            v-for="dict in dict.type.equip_repair_sratus"
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
          v-hasPermi="['pur:order:add']"
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
          v-hasPermi="['pur:order:edit']"
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
          v-hasPermi="['pur:order:remove']"
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
          v-hasPermi="['pur:order:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="orderList" @selection-change="handleSelectionChange" :columns="columns">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="序号" align="center" v-if="columns[0].visible" prop="orderId"/>
      <el-table-column label="订单编号" :show-overflow-tooltip="true" align="center" v-if="columns[1].visible"
                       prop="orderNo"
      />
      <el-table-column label="供应商ID" align="center" v-if="columns[2].visible" prop="supplierId">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.equip_order" :value="scope.row.supplierId"/>
        </template>
      </el-table-column>
      <el-table-column label="供应商" :show-overflow-tooltip="true" align="center" v-if="columns[3].visible"
                       prop="supplierName"
      />
      <el-table-column label="申请部门ID" align="center" v-if="columns[4].visible" prop="deptId">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.equip_dept" :value="scope.row.deptId"/>
        </template>
      </el-table-column>
      <el-table-column label="申请部门" :show-overflow-tooltip="true" align="center" v-if="columns[5].visible"
                       prop="deptName"
      />
      <el-table-column label="申请人ID" align="center" v-if="columns[6].visible" prop="applyUserId">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.equip_user" :value="scope.row.applyUserId"/>
        </template>
      </el-table-column>
      <el-table-column label="申请人" :show-overflow-tooltip="true" align="center" v-if="columns[7].visible"
                       prop="applyUserName"
      />
      <el-table-column label="申请时间" align="center" v-if="columns[8].visible" prop="applyTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.applyTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="预计到货时间" align="center" v-if="columns[9].visible" prop="arriveTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.arriveTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="预算单价" :show-overflow-tooltip="true" align="center" v-if="columns[10].visible"
                       prop="unitAmount"
      />
      <el-table-column label="预算金额" :show-overflow-tooltip="true" align="center" v-if="columns[11].visible"
                       prop="budgetAmount"
      />
      <el-table-column label="采购原因" :show-overflow-tooltip="true" align="center" v-if="columns[12].visible"
                       prop="purchasingReason"
      />
      <el-table-column label="相关附件" align="center" v-if="columns[13].visible" prop="appendix" width="100">
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
      <el-table-column label="流程状态" :show-overflow-tooltip="true" align="center" v-if="columns[14].visible"
                       prop="processStatus"
      />
      <el-table-column label="流程实例ID" align="center" v-if="columns[15].visible" prop="processInstanceId">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_process_category" :value="scope.row.processInstanceId"/>
        </template>
      </el-table-column>
      <el-table-column label="流程自定义ID" :show-overflow-tooltip="true" align="center" v-if="columns[16].visible"
                       prop="deployId"
      />
      <el-table-column label="状态" align="center" v-if="columns[17].visible" prop="orderStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.equip_repair_sratus" :value="scope.row.orderStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="备注" :show-overflow-tooltip="true" align="center" v-if="columns[18].visible"
                       prop="remark"
      />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="200" fixed="right">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['pur:order:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleProcess(scope.row)"
            v-hasPermi="['pur:order:list']"
          >查看流程
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['pur:order:remove']"
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

    <!-- 添加或修改采购订单对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1000px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="供应商" prop="supplierId">
          <el-select v-model="form.supplierId" placeholder="请选择供应商" @change="handleSupplierChange">
            <el-option
              v-for="item in supplierList"
              :key="item.supplierId"
              :label="item.supplierName"
              :value="item.supplierId"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="申请部门" prop="deptId">
          <el-select v-model="form.deptId" placeholder="请选择申请部门" @change="handleDeptChange">
            <el-option
              v-for="item in deptList"
              :key="item.deptId"
              :label="item.deptName"
              :value="item.deptId"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="申请人" prop="applyUserId">
          <el-select v-model="form.applyUserId" placeholder="请选择申请人" @change="handleApplyUserChange">
            <el-option
              v-for="item in userList"
              :key="item.userId"
              :label="item.nickName"
              :value="item.userId"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="申请时间" prop="applyTime">
          <el-date-picker clearable
                          v-model="form.applyTime"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择申请时间"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="预计到货时间" prop="arriveTime">
          <el-date-picker clearable
                          v-model="form.arriveTime"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择预计到货时间"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="预算单价" prop="unitAmount">
          <el-input-number :min="0" :precision="2" v-model="form.unitAmount" placeholder="请输入预算单价"/>
        </el-form-item>
        <el-form-item label="预算金额" prop="budgetAmount">
          <el-input-number :min="0" :precision="2" v-model="form.budgetAmount" placeholder="请输入预算金额"/>
        </el-form-item>
        <el-form-item label="采购原因" prop="purchasingReason">
          <el-input v-model="form.purchasingReason" type="textarea" placeholder="请输入内容"/>
        </el-form-item>
        <el-form-item label="相关附件" prop="appendix">
          <file-upload v-model="form.appendix"/>
        </el-form-item>
        <!--        <el-form-item label="流程实例" prop="processInstanceId">-->
        <!--          <el-select v-model="form.processInstanceId" placeholder="请选择流程实例">-->
        <!--            <el-option-->
        <!--              v-for="dict in dict.type.sys_process_category"-->
        <!--              :key="dict.value"-->
        <!--              :label="dict.label"-->
        <!--              :value="dict.value"-->
        <!--            ></el-option>-->
        <!--          </el-select>-->
        <!--        </el-form-item>-->
        <el-divider content-position="center">设备采购订单明细信息</el-divider>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAddEquipPurchaseOrderItem">添加
            </el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" icon="el-icon-delete" size="mini" @click="handleDeleteEquipPurchaseOrderItem">
              删除
            </el-button>
          </el-col>
        </el-row>
        <el-table :data="equipPurchaseOrderItemList" :row-class-name="rowEquipPurchaseOrderItemIndex"
                  @selection-change="handleEquipPurchaseOrderItemSelectionChange" ref="equipPurchaseOrderItem"
        >
          <el-table-column type="selection" width="50" align="center"/>
          <el-table-column label="序号" align="center" prop="index" width="50"/>
          <el-table-column label="设备名称" prop="equipmentName" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.equipmentName" placeholder="请输入设备名称"/>
            </template>
          </el-table-column>
          <el-table-column label="规格型号" prop="specification" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.specification" placeholder="请输入规格型号"/>
            </template>
          </el-table-column>
          <el-table-column label="数量" prop="quantity" width="150">
            <template slot-scope="scope">
              <el-input-number style="width: 100%" :min="0" v-model="scope.row.quantity" placeholder="请输入数量"/>
            </template>
          </el-table-column>
          <el-table-column label="单价" prop="unitPrice" width="150">
            <template slot-scope="scope">
              <el-input-number style="width: 100%" :min="0" :precision="2" v-model="scope.row.unitPrice"
                               placeholder="请输入单价"
              />
            </template>
          </el-table-column>
          <el-table-column label="金额" prop="totalPrice" width="150">
            <template slot-scope="scope">
              <el-input-number style="width: 100%" :min="0" :precision="2" v-model="scope.row.totalPrice"
                               placeholder="请输入金额"
              />
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
    <!--    <FlowRecordList :visible.sync="dialogVisible"-->
    <!--                    :deploy-id="deployId"-->
    <!--                    :proc-ins-id="procInsId"-->
    <!--    />-->
  </div>
</template>

<script>
import { addOrder, delOrder, getOrder, listOrder, updateOrder } from '@/api/pur/order'
import { listSupplier } from '@/api/pur/supplier'
import { listDept } from '@/api/system/dept'
import { listUser } from '@/api/system/user'
import TaskDetail from '@/components/TaskDetail/index.vue'
import FlowRecordList from '@/components/FlowRecordList/index.vue'

export default {
  name: 'Order',
  components: { FlowRecordList, TaskDetail },
  dicts: ['equip_repair_sratus', 'sys_process_category'],
  data() {
    return {
      baseUrl: process.env.VUE_APP_BASE_API,
      //表格展示列
      columns: [
        { key: 0, label: '序号', visible: true },
        { key: 1, label: '订单编号', visible: true },
        { key: 2, label: '供应商ID', visible: false },
        { key: 3, label: '供应商', visible: true },
        { key: 4, label: '申请部门ID', visible: false },
        { key: 5, label: '申请部门', visible: true },
        { key: 6, label: '申请人ID', visible: false },
        { key: 7, label: '申请人', visible: true },
        { key: 8, label: '申请时间', visible: true },
        { key: 9, label: '预计到货时间', visible: true },
        { key: 10, label: '预算单价', visible: true },
        { key: 11, label: '预算金额', visible: true },
        { key: 12, label: '采购原因', visible: false },
        { key: 13, label: '相关附件', visible: false },
        { key: 14, label: '流程状态', visible: false },
        { key: 15, label: '流程实例ID', visible: false },
        { key: 16, label: '流程自定义ID', visible: false },
        { key: 17, label: '状态', visible: false },
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
      // 子表选中数据
      checkedEquipPurchaseOrderItem: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 采购订单表格数据
      orderList: [],
      // 设备采购订单明细表格数据
      equipPurchaseOrderItemList: [],
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 供应商列表
      supplierList: [],
      // 部门列表
      deptList: [],
      // 用户列表
      userList: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        supplierName: null,
        deptName: null,
        applyUserName: null,
        orderStatus: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {}
    }
  },
  created() {
    this.getList()
    this.getSupplierList()
    this.getDeptList()
  },
  methods: {
    //获取文件名 此功能只可以下载只有一个文件的
    getFileName(filePath) {
      if (filePath == null) {
        return ''
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
    /** 查询采购订单列表 */
    getList() {
      this.loading = true
      listOrder(this.queryParams).then(response => {
        this.orderList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    // 获取供应商列表
    getSupplierList() {
      listSupplier({}).then(response => {
        this.supplierList = response.rows
      })
    },
    // 获取部门列表
    getDeptList() {
      listDept({}).then(response => {
        this.deptList = response.data
      })
    },
    // 获取用户列表
    getUserList(deptId) {
      const params = { deptId: deptId }
      listUser(params).then(response => {
        this.userList = response.rows
      })
    },
    // 部门选择变化
    handleDeptChange(value) {
      // 清空已选择的申请人
      this.form.applyUserId = null

      // 根据部门ID获取该部门下的用户
      if (value) {
        this.getUserList(value)
      } else {
        this.userList = []
      }

      // 设置部门名称
      const dept = this.deptList.find(item => item.deptId === value)
      if (dept) {
        this.form.deptName = dept.deptName
      }
    },
    // 申请人选择变化
    handleApplyUserChange(value) {
      const user = this.userList.find(item => item.userId === value)
      if (user) {
        this.form.applyUserName = user.nickName
      }
    },
    // 供应商选择变化
    handleSupplierChange(value) {
      const supplier = this.supplierList.find(item => item.supplierId === value)
      if (supplier) {
        this.form.supplierName = supplier.supplierName
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
        orderId: null,
        orderNo: null,
        supplierId: null,
        supplierName: null,
        deptId: null,
        deptName: null,
        applyUserId: null,
        applyUserName: null,
        applyTime: null,
        budgetAmount: null,
        appendix: null,
        processInstanceId: null,
        orderStatus: null,
        currentTask: null,
        assignee: null,
        remark: null,
        createBy: null,
        createTime: null,
        updateTime: null
      }
      this.equipPurchaseOrderItemList = []
      this.userList = []
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
      this.ids = selection.map(item => item.orderId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = '添加采购订单'
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const orderId = row.orderId || this.ids
      getOrder(orderId).then(response => {
        this.form = response.data
        this.equipPurchaseOrderItemList = response.data.equipPurchaseOrderItemList
        this.open = true
        this.title = '修改采购订单'
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          this.form.equipPurchaseOrderItemList = this.equipPurchaseOrderItemList
          if (this.form.orderId != null) {
            console.log(this.form)
            updateOrder(this.form).then(response => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addOrder(this.form).then(response => {
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
      const orderIds = row.orderId || this.ids
      this.$modal.confirm('是否确认删除采购订单编号为"' + orderIds + '"的数据项？').then(function() {
        return delOrder(orderIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess('删除成功')
      }).catch(() => {
      })
    },
    /** 设备采购订单明细序号 */
    rowEquipPurchaseOrderItemIndex({ row, rowIndex }) {
      row.index = rowIndex + 1
    },
    /** 设备采购订单明细添加按钮操作 */
    handleAddEquipPurchaseOrderItem() {
      let obj = {}
      obj.equipmentName = ''
      obj.specification = ''
      obj.quantity = ''
      obj.unitPrice = ''
      obj.totalPrice = ''
      this.equipPurchaseOrderItemList.push(obj)
    },
    /** 设备采购订单明细删除按钮操作 */
    handleDeleteEquipPurchaseOrderItem() {
      if (this.checkedEquipPurchaseOrderItem.length == 0) {
        this.$modal.msgError('请先选择要删除的设备采购订单明细数据')
      } else {
        const equipPurchaseOrderItemList = this.equipPurchaseOrderItemList
        const checkedEquipPurchaseOrderItem = this.checkedEquipPurchaseOrderItem
        this.equipPurchaseOrderItemList = equipPurchaseOrderItemList.filter(function(item) {
          return checkedEquipPurchaseOrderItem.indexOf(item.index) == -1
        })
      }
    },
    /** 复选框选中数据 */
    handleEquipPurchaseOrderItemSelectionChange(selection) {
      this.checkedEquipPurchaseOrderItem = selection.map(item => item.index)
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('pur/order/export', {
        ...this.queryParams
      }, `order_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
