<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="设备来源" prop="isOriginal">
        <el-select v-model="queryParams.isOriginal" placeholder="请选择设备来源" clearable>
          <el-option
            v-for="dict in dict.type.equip_source"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="设备名称" prop="equName">
        <el-input
          v-model="queryParams.equName"
          placeholder="请输入设备名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="管理级别" prop="equipLevels">
        <el-select v-model="queryParams.equipLevels" placeholder="请选择管理级别" clearable>
          <el-option
            v-for="dict in dict.type.equip_levels"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="设备状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择设备状态" clearable>
          <el-option
            v-for="dict in dict.type.equip_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="动静设备" prop="dynamicStaticEquipment">
        <el-select v-model="queryParams.dynamicStaticEquipment" placeholder="请选择动静设备" clearable>
          <el-option
            v-for="dict in dict.type.static_dynamic"
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
          v-hasPermi="['equip:ledger:add']"
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
          v-hasPermi="['equip:ledger:edit']"
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
          v-hasPermi="['equip:ledger:remove']"
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
          v-hasPermi="['equip:ledger:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="ledgerList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="序号" align="center" v-if="columns[0].visible" prop="equipId"/>
      <el-table-column label="设备来源" align="center" v-if="columns[1].visible" prop="isOriginal">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.equip_source" :value="scope.row.isOriginal"/>
        </template>
      </el-table-column>
      <el-table-column label="设备名称" :show-overflow-tooltip="true" align="center" v-if="columns[2].visible"
                       prop="equName"
      />
      <el-table-column label="设备位号" :show-overflow-tooltip="true" align="center" v-if="columns[3].visible"
                       prop="equCode"
      />
      <el-table-column label="设备类型" align="center" v-if="columns[4].visible" prop="type">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.equip_type" :value="scope.row.type"/>
        </template>
      </el-table-column>
      <el-table-column label="管理级别" align="center" v-if="columns[5].visible" prop="equipLevels">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.equip_levels" :value="scope.row.equipLevels"/>
        </template>
      </el-table-column>
      <el-table-column label="动静设备" align="center" v-if="columns[15].visible" prop="dynamicStaticEquipment">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.static_dynamic" :value="scope.row.dynamicStaticEquipment"/>
        </template>
      </el-table-column>
      <el-table-column label="规格型号" :show-overflow-tooltip="true" align="center" v-if="columns[6].visible"
                       prop="specification"
      />
      <el-table-column label="设备材质" :show-overflow-tooltip="true" align="center" v-if="columns[16].visible"
                       prop="equipmentMaterial"
      />
      <el-table-column label="采购日期" align="center" v-if="columns[17].visible" prop="purchaseDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.purchaseDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="出厂日期" align="center" v-if="columns[18].visible" prop="factoryDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.factoryDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="投入使用日期" align="center" v-if="columns[7].visible" prop="putIntroDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.putIntroDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="设计/操作参数" :show-overflow-tooltip="true" align="center" v-if="columns[19].visible"
                       prop="designOperatingParameters"
      />
      <el-table-column label="安装位置" align="center" v-if="columns[8].visible" prop="installLocation">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.equip_location" :value="scope.row.installLocation"/>
        </template>
      </el-table-column>
      <el-table-column label="供应商" :show-overflow-tooltip="true" align="center" v-if="columns[9].visible"
                       prop="supplierName"
      />
      <el-table-column label="所属部门" :show-overflow-tooltip="true" align="center" v-if="columns[10].visible"
                       prop="departmentName"
      />
      <el-table-column label="责任人" :show-overflow-tooltip="true" align="center" v-if="columns[11].visible"
                       prop="responsiblePerson"
      />
      <el-table-column label="设备状态" align="center" v-if="columns[12].visible" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.equip_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="备注" :show-overflow-tooltip="true" align="center" v-if="columns[13].visible"
                       prop="remark"
      />
      <el-table-column label="特种设备" align="center" prop="specialEquipment" v-if="columns[14].visible">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_yes_no" :value="scope.row.specialEquipment"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['equip:ledger:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['equip:ledger:remove']"
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

    <!-- 添加或修改设备台账对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="设备来源" prop="isOriginal">
          <el-select v-model="form.isOriginal" placeholder="请选择设备来源" @change="handleIsOriginalChange">
            <el-option
              v-for="dict in dict.type.equip_source"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="设备名称" prop="equName" v-if="form.isOriginal !== 0">
          <el-input v-model="form.equName" placeholder="请输入设备名称"/>
        </el-form-item>
        <el-form-item label="设备名称" v-else prop="equName">
          <el-select v-model="form.equName" placeholder="请选择设备名称"
                     remote
                     filterable
                     reserve-keyword
                     :remote-method="remotePurOrderList"
          >
            <el-option
              v-for="item in purOrderList"
              :key="item.orderId"
              :label="item.equipName"
              :value="item.equipName"
            >
              <span style="float: left">{{ item.equipName }}</span>
              <span style="float: right; color: #8492a6; font-size: 13px">{{ item.orderNo }}</span>
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="设备位号" prop="equCode">
          <el-input v-model="form.equCode" placeholder="请输入设备位号"/>
        </el-form-item>
        <el-form-item label="设备类型" prop="type">
          <el-select v-model="form.type" placeholder="请选择设备类型">
            <el-option
              v-for="dict in dict.type.equip_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="管理级别" prop="equipLevels">
          <el-select v-model="form.equipLevels" placeholder="请选择管理级别">
            <el-option
              v-for="dict in dict.type.equip_levels"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="动静设备" prop="dynamicStaticEquipment">
          <el-select v-model="form.dynamicStaticEquipment" placeholder="请选择动静设备">
            <el-option
              v-for="dict in dict.type.static_dynamic"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="规格型号" prop="specification">
          <el-input v-model="form.specification" placeholder="请输入规格型号"/>
        </el-form-item>
        <el-form-item label="设备材质" prop="equipmentMaterial">
          <el-input v-model="form.equipmentMaterial" placeholder="请输入设备材质"/>
        </el-form-item>
        <el-form-item label="采购日期" prop="purchaseDate">
          <el-date-picker clearable
                          v-model="form.purchaseDate"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择采购日期"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="出厂日期" prop="factoryDate">
          <el-date-picker clearable
                          v-model="form.factoryDate"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择出厂日期"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="投用日期" prop="putIntroDate">
          <el-date-picker clearable
                          v-model="form.putIntroDate"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择投用日期"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="设计/操作参数" prop="designOperatingParameters">
          <el-input v-model="form.designOperatingParameters" placeholder="请输入设计/操作参数"/>
        </el-form-item>
        <el-form-item label="安装位置" prop="installLocation">
          <el-select v-model="form.installLocation" placeholder="请选择安装位置">
            <el-option
              v-for="dict in dict.type.equip_location"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="供应商" prop="supplierId">
          <!-- 供应商下拉选择 -->
          <el-select
            v-model="form.supplierId"
            placeholder="请选择供应商"
            @change="handleSupplierChange"
          >
            <el-option
              v-for="item in supplierList"
              :key="item.supplierId"
              :label="item.supplierName"
              :value="item.supplierId"
            ></el-option>
          </el-select>
          <!-- 隐藏的供应商名称输入框，用于保存供应商名称 -->
          <el-input
            v-model="form.supplierName"
            style="display: none;"
          />
        </el-form-item>
        <el-form-item label="责任部门" prop="departmentId">
          <el-select v-model="form.departmentId" placeholder="请选择责任部门" @change="handleDepartmentChange">
            <el-option
              v-for="dept in deptList"
              :key="dept.deptId"
              :label="dept.deptName"
              :value="dept.deptId"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="责任人" prop="responsiblePersonId">
          <el-select v-model="form.responsiblePersonId" placeholder="请选择责任人"
                     @change="handleResponsiblePersonChange"
          >
            <el-option
              v-for="user in userList"
              :key="user.userId"
              :label="user.nickName"
              :value="user.userId"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="设备状态" prop="status">
          <el-select v-model="form.status" placeholder="请选择设备状态">
            <el-option
              v-for="dict in dict.type.equip_status"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="特种设备" prop="specialEquipment">
          <el-select v-model="form.specialEquipment" placeholder="请选择是否为特种设备">
            <el-option
              v-for="dict in dict.type.sys_yes_no"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
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
import { addLedger, delLedger, getLedger, listLedger, updateLedger } from '@/api/equip/ledger'
import { listSupplier } from '@/api/pur/supplier'
import { listUser } from '@/api/system/user'
import { listDept } from '@/api/system/dept'
import { listOrder } from '@/api/pur/order'

export default {
  name: 'Ledger',
  dicts: ['equip_source', 'equip_levels', 'equip_dept', 'equip_user', 'equip_status', 'equip_order', 'equip_type', 'equip_location', 'sys_yes_no', 'static_dynamic'],
  data() {
    return {
      //表格展示列
      columns: [
        { key: 0, label: '序号', visible: false },
        { key: 1, label: '设备来源', visible: true },
        { key: 2, label: '设备名称', visible: true },
        { key: 3, label: '设备位号', visible: true },
        { key: 4, label: '设备类型', visible: true },
        { key: 5, label: '管理级别', visible: true },
        { key: 6, label: '规格型号', visible: true },
        { key: 16, label: '设备材质', visible: true },
        { key: 17, label: '采购日期', visible: true },
        { key: 18, label: '出厂日期', visible: true },
        { key: 7, label: '投入使用日期', visible: true },
        { key: 19, label: '设计/操作参数', visible: true },
        { key: 8, label: '安装位置', visible: true },
        { key: 9, label: '供应商', visible: true },
        { key: 10, label: '所属部门', visible: true },
        { key: 11, label: '责任人', visible: true },
        { key: 12, label: '设备状态', visible: true },
        { key: 13, label: '备注', visible: false },
        { key: 14, label: '特种设备', visible: true },
        { key: 15, label: '动静设备', visible: true }
      ],
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
      // 设备台账表格数据
      ledgerList: [],
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        isOriginal: null,
        equName: null,
        equipLevels: null,
        status: null
      },
      // 表单参数
      form: {},
      // 供应商列表
      supplierList: [],
      // 部门列表
      deptList: [],
      // 用户列表（责任人）
      userList: [],
      // 表单校验
      rules: {
        equName: [
          { required: true, message: '设备名称不能为空', trigger: 'blur' }
        ],
        equCode: [
          { required: true, message: '设备位号不能为空', trigger: 'blur' }
        ]
      },
      purOrderList: []
    }
  },
  created() {
    this.getList()
    this.getPurOrderList()
  },
  methods: {
    /** 查询采购订单列表 */
    getPurOrderList() {
      listOrder({ pageNum: 1, pageSize: 100 }).then(response => {
        this.purOrderList = response.rows
      })
    },
    remotePurOrderList(keyword) {
      listOrder({ pageNum: 1, pageSize: 100, equipName: keyword }).then(response => {
        this.purOrderList = response.rows
      })
    },
    /** 查询设备台账列表 */
    getList() {
      this.loading = true
      listLedger(this.queryParams).then(response => {
        this.ledgerList = response.rows
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
        equipId: null,
        isOriginal: null,
        equName: null,
        equCode: null,
        type: null,
        equipLevels: null,
        dynamicStaticEquipment: null,
        specification: null,
        equipmentMaterial: null,
        purchaseDate: null,
        factoryDate: null,
        putIntroDate: null,
        designOperatingParameters: null,
        installLocation: null,
        supplierId: null,
        supplierName: null,
        departmentId: null,
        departmentName: null,
        responsiblePersonId: null,
        responsiblePerson: null,
        status: null,
        specialEquipment: null,
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
      this.ids = selection.map(item => item.equipId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.getSupplierList()
      this.getDeptList()
      this.open = true
      this.title = '添加设备台账'
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const equipId = row.equipId || this.ids
      getLedger(equipId).then(response => {
        this.form = response.data
        this.getSupplierList()
        this.getDeptList()
        // 获取当前部门下的用户
        if (this.form.departmentId) {
          this.getUserList(this.form.departmentId)
        }
        this.open = true
        this.title = '修改设备台账'
      })
    },
    // 获取供应商列表
    getSupplierList() {
      listSupplier().then(response => {
        this.supplierList = response.rows
      })
    },
    // 获取部门列表
    getDeptList() {
      listDept().then(response => {
        this.deptList = response.data
      })
    },
    // 获取指定部门下的用户列表
    getUserList(deptId) {
      const params = { deptId: deptId }
      listUser(params).then(response => {
        this.userList = response.rows
      })
    },
    // 设备来源改变时触发
    handleIsOriginalChange(value) {
      // 清空供应商选择
      this.form.supplierId = null
      this.form.supplierName = null
    },
    // 供应商选择变化时触发
    handleSupplierChange(value) {
      if (value) {
        const supplier = this.supplierList.find(item => item.supplierId === value)
        if (supplier) {
          this.form.supplierName = supplier.supplierName
        }
      } else {
        this.form.supplierName = null
      }
    },
    // 部门选择变化时触发
    handleDepartmentChange(value) {
      // 清空之前选择的责任人
      this.form.responsiblePersonId = null
      this.form.responsiblePerson = null

      // 如果选择了部门，则获取该部门下的用户列表
      if (value) {
        this.getUserList(value)
      } else {
        // 如果没有选择部门，则清空用户列表
        this.userList = []
      }
    },
    // 责任人选择变化时触发
    handleResponsiblePersonChange(value) {
      if (value) {
        const user = this.userList.find(item => item.userId === value)
        if (user) {
          this.form.responsiblePerson = user.nickName
        }
      } else {
        this.form.responsiblePerson = null
      }
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          if (this.form.equipId != null) {
            updateLedger(this.form).then(response => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addLedger(this.form).then(response => {
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
      const equipIds = row.equipId || this.ids
      this.$modal.confirm('是否确认删除设备台账编号为"' + equipIds + '"的数据项？').then(function() {
        return delLedger(equipIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess('删除成功')
      }).catch(() => {
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('equip/ledger/export', {
        ...this.queryParams
      }, `ledger_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
