<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="统计编号" prop="statNo">
        <el-input
          v-model="queryParams.statNo"
          placeholder="请输入统计编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="设备" prop="deviceId">
        <el-select v-model="queryParams.deviceId" placeholder="请选择设备" clearable>
          <el-option
            v-for="item in ledgerList"
            :key="item.equipId"
            :label="item.equName"
            :value="item.equipId"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="统计状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择统计状态" clearable>
          <el-option
            v-for="dict in dict.type.equip_runtime_status"
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
          v-hasPermi="['equip:stat:add']"
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
          v-hasPermi="['equip:stat:edit']"
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
          v-hasPermi="['equip:stat:remove']"
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
          v-hasPermi="['equip:stat:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="statList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="序号" align="center" v-if="columns[0].visible" prop="statId"/>
      <el-table-column label="统计编号" :show-overflow-tooltip="true" align="center" v-if="columns[1].visible"
                       prop="statNo"
      />
      <el-table-column label="设备名称" :show-overflow-tooltip="true" align="center" v-if="columns[2].visible"
                       prop="deviceName"
      />
      <el-table-column label="设备位号" :show-overflow-tooltip="true" align="center" v-if="columns[3].visible"
                       prop="deviceCode"
      />
      <el-table-column label="开机时间" align="center" v-if="columns[4].visible" prop="startTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.startTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="停机时间" align="center" v-if="columns[5].visible" prop="stopTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.stopTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="运行时长(h)" align="center" prop="totalRuntime" v-if="columns[6].visible">
        <template slot-scope="scope">
          <span v-if="scope.row.startTime && !scope.row.stopTime">
            <!-- 实时计算运行时长 -->
            {{ Math.floor((Date.now() - new Date(scope.row.startTime).getTime()) / (1000 * 60 * 60)) }}
          </span>
          <span v-else>{{ scope.row.totalRuntime }}</span>
        </template>
      </el-table-column>
      <el-table-column label="统计状态" align="center" v-if="columns[7].visible" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.equip_runtime_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="停机原因" :show-overflow-tooltip="true" align="center" v-if="columns[8].visible"
                       prop="shutdownReason"
      />
      <el-table-column label="备注" :show-overflow-tooltip="true" align="center" v-if="columns[9].visible"
                       prop="remark"
      />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['equip:stat:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['equip:stat:remove']"
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

    <!-- 添加或修改设备运行管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="900px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <!-- 删除统计编号输入框 -->
        <el-form-item label="设备" prop="deviceId">
          <el-row>
            <el-col :span="20">
              <el-select
                v-model="form.deviceId"
                placeholder="请选择设备"
                @change="handleDeviceChange"
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
        <el-form-item label="设备位号" prop="deviceCode">
          <el-input v-model="form.deviceCode" placeholder="设备位号" disabled/>
        </el-form-item>
        <el-form-item label="开机时间" prop="startTime">
          <el-date-picker clearable
                          v-model="form.startTime"
                          type="datetime"
                          value-format="yyyy-MM-dd HH:mm:ss"
                          placeholder="请选择开机时间"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="停机时间" prop="stopTime">
          <el-date-picker clearable
                          v-model="form.stopTime"
                          type="datetime"
                          value-format="yyyy-MM-dd HH:mm:ss"
                          placeholder="请选择停机时间"
          >
          </el-date-picker>
          <div v-if="!form.startTime && form.stopTime" style="color: #E6A23C; font-size: 12px; margin-top: 5px;">
            注意：添加停机信息将更新最近一次的开机记录，不会生成新的统计编号
          </div>
        </el-form-item>
        <!-- 移除状态选择框，因为状态由系统自动设置 -->
        <el-form-item label="停机原因" prop="shutdownReason">
          <el-input v-model="form.shutdownReason" placeholder="请输入停机原因"/>
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
  </div>
</template>

<script>
import { listStat, getStat, delStat, addStat, updateStat } from '@/api/equip/stat'
import { listLedger } from '@/api/equip/ledger'

export default {
  name: 'Stat',
  dicts: ['equip_operation_type', 'equip_runtime_status', 'equip_type', 'equip_levels', 'equip_location'],
  data() {
    return {
      //表格展示列
      columns: [
        { key: 0, label: '序号', visible: true },
        { key: 1, label: '统计编号', visible: true },
        { key: 2, label: '设备名称', visible: true },
        { key: 3, label: '设备位号', visible: true },
        { key: 4, label: '开机时间', visible: true },
        { key: 5, label: '停机时间', visible: true },
        { key: 6, label: '运行时长(h)', visible: true },
        { key: 7, label: '统计状态', visible: true },
        { key: 8, label: '停机原因', visible: true },
        { key: 9, label: '备注', visible: false }
      ],
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 子表选中数据
      checkedEquipRuntimeDatail: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 设备运行管理表格数据
      statList: [],
      // 设备台账列表
      ledgerList: [],
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
        statNo: null,
        deviceId: null,
        deviceName: null,
        status: null
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
        // 删除统计编号的校验规则
        deviceId: [
          { required: true, message: '设备不能为空', trigger: 'change' }
        ]
      },
      // 定时器
      refreshTimer: null
    }
  },
  created() {
    this.getList()
    this.getLedgerList()
    this.getEquipList()
    // 启动定时刷新，每分钟刷新一次运行时长
    this.startAutoRefresh()
  },
  beforeDestroy() {
    // 组件销毁前清除定时器
    this.stopAutoRefresh()
  },
  methods: {
    /** 启动自动刷新 */
    startAutoRefresh() {
      this.refreshTimer = setInterval(() => {
        // 只在列表页面且有运行中设备时才刷新
        if (!this.open && this.statList.some(item => item.stopTime === null && item.startTime !== null)) {
          this.getList()
        }
      }, 60000) // 每分钟刷新一次
    },
    /** 停止自动刷新 */
    stopAutoRefresh() {
      if (this.refreshTimer) {
        clearInterval(this.refreshTimer)
        this.refreshTimer = null
      }
    },
    /** 查询设备台账列表 */
    getLedgerList() {
      listLedger({ pageSize: 10000 }).then(response => {
        this.ledgerList = response.rows
      })
    },
    /** 查询设备运行管理列表 */
    getList() {
      this.loading = true
      listStat(this.queryParams).then(response => {
        this.statList = response.rows
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
        statId: null,
        statNo: null,
        deviceId: null,
        deviceName: null,
        deviceCode: null,
        startRecordId: null,
        startTime: null,
        stopRecordId: null,
        stopTime: null,
        totalRuntime: null,
        status: null,
        shutdownReason: null,
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
      this.ids = selection.map(item => item.statId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = '添加设备运行管理'
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const statId = row.statId || this.ids
      getStat(statId).then(response => {
        this.form = response.data
        // 确保设备ID是数字类型
        if (this.form.deviceId) {
          this.form.deviceId = parseInt(this.form.deviceId)
        }
        this.open = true
        this.title = '修改设备运行管理'
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          if (this.form.statId != null) {
            updateStat(this.form).then(response => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            // 在新增前确保设备名称正确设置
            const selectedDevice = this.equipList.find(item =>
              item.equipId === this.form.deviceId)
            if (selectedDevice) {
              this.form.deviceName = selectedDevice.equName
              this.form.deviceCode = selectedDevice.equCode
            }

            addStat(this.form).then(response => {
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
      const statIds = row.statId || this.ids
      this.$modal.confirm('是否确认删除设备运行管理编号为"' + statIds + '"的数据项？').then(function() {
        return delStat(statIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess('删除成功')
      }).catch(() => {
      })
    },
    /** 设备选择变化处理 */
    handleDeviceChange(val) {
      // 从设备台账列表中查找选中的设备
      const selectedDevice = this.equipList.find(item =>
        item.equipId === val)
      if (selectedDevice) {
        // 设置设备名称和设备位号
        this.form.deviceName = selectedDevice.equName
        this.form.deviceCode = selectedDevice.equCode
      } else {
        this.form.deviceName = ''
        this.form.deviceCode = ''
      }
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('equip/stat/export', {
        ...this.queryParams
      }, `stat_${new Date().getTime()}.xlsx`)
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
      this.form.deviceId = row.equipId
      this.form.deviceName = row.equName
      this.form.deviceCode = row.equCode
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
