<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="设备名称" prop="equipmentName">
        <el-input
          v-model="queryParams.equipmentName"
          placeholder="请输入设备名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="管理级别" prop="equipLevels">
        <el-select v-model="queryParams.equipLevels" placeholder="请选择设备管理级别" clearable>
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
          v-hasPermi="['anticorrosion:base:add']"
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
          v-hasPermi="['anticorrosion:base:edit']"
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
          v-hasPermi="['anticorrosion:base:remove']"
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
          v-hasPermi="['anticorrosion:base:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="baseList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="序号" align="center" v-if="columns[0].visible" prop="id"/>
      <el-table-column label="设备ID" align="center" v-if="columns[1].visible" prop="equipmentId"/>
      <el-table-column label="设备名称" :show-overflow-tooltip="true" align="center" v-if="columns[2].visible"
                       prop="equipmentName"
      />
      <el-table-column label="设备位号" :show-overflow-tooltip="true" align="center" v-if="columns[3].visible"
                       prop="equipmentCode"
      />
      <el-table-column label="设备管理级别" align="center" v-if="columns[4].visible" prop="equipLevels">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.equip_levels" :value="scope.row.equipLevels"/>
        </template>
      </el-table-column>
      <el-table-column label="规格型号" :show-overflow-tooltip="true" align="center" v-if="columns[5].visible"
                       prop="specification"
      />
      <el-table-column label="易腐蚀部位" :show-overflow-tooltip="true" align="center" v-if="columns[6].visible"
                       prop="corrosionParts"
      />
      <el-table-column label="材质" :show-overflow-tooltip="true" align="center" v-if="columns[7].visible"
                       prop="material"
      >
        <template slot-scope="scope">
          <dict-tag :options="dict.type.special_material" :value="scope.row.material"/>
        </template>
      </el-table-column>
      <el-table-column label="介质" :show-overflow-tooltip="true" align="center" v-if="columns[8].visible"
                       prop="medium"
      />
      <el-table-column label="温度(℃)" :show-overflow-tooltip="true" align="center" v-if="columns[9].visible"
                       prop="temperature"
      />
      <el-table-column label="压力(MPa)" :show-overflow-tooltip="true" align="center" v-if="columns[10].visible"
                       prop="pressure"
      />
      <el-table-column label="设计厚度(mm)" :show-overflow-tooltip="true" align="center" v-if="columns[11].visible"
                       prop="originalThickness"
      />
      <el-table-column label="最小安全厚度(mm)" :show-overflow-tooltip="true" align="center" v-if="columns[12].visible"
                       prop="minAllowableThickness"
      />
      <el-table-column label="检测周期" align="center" v-if="columns[13].visible" prop="defaultInspectionCycle">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.equip_inspection_cycle" :value="scope.row.defaultInspectionCycle"/>
        </template>
      </el-table-column>
      <el-table-column label="控制措施" :show-overflow-tooltip="true" align="center" v-if="columns[14].visible"
                       prop="controlMeasures"
      />
      <el-table-column label="备注" :show-overflow-tooltip="true" align="center" v-if="columns[15].visible"
                       prop="remark"
      />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" fixed="right">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['anticorrosion:base:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['anticorrosion:base:remove']"
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

    <!-- 添加或修改防腐基础信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="900px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="设备" prop="equipmentId">
          <el-row>
            <el-col :span="20">
              <el-select
                v-model="form.equipmentId"
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
        <el-form-item label="设备位号" prop="equipmentCode">
          <el-input v-model="form.equipmentCode" placeholder="设备位号" disabled/>
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
        <el-form-item label="规格型号" prop="specification">
          <el-input v-model="form.specification" placeholder="请输入规格型号" readonly/>
        </el-form-item>
        <el-form-item label="易腐蚀部位" prop="corrosionParts">
          <el-input v-model="form.corrosionParts" placeholder="请输入易腐蚀部位"/>
        </el-form-item>
        <el-form-item label="材质" prop="material">
          <el-select v-model="form.material" placeholder="请选择材质">
            <el-option
              v-for="dict in dict.type.special_material"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="介质" prop="medium">
          <el-input v-model="form.medium" placeholder="请输入介质"/>
        </el-form-item>
        <el-form-item label="温度（℃）" prop="temperature">
          <el-input v-model="form.temperature" placeholder="请输入温度"/>
        </el-form-item>
        <el-form-item label="压力(MPa)" prop="pressure">
          <el-input v-model="form.pressure" placeholder="请输入压力"/>
        </el-form-item>
        <el-form-item label="设计厚度(mm)" prop="originalThickness">
          <el-input v-model="form.originalThickness" placeholder="请输入设计厚度(mm)"/>
        </el-form-item>
        <el-form-item label="最小安全厚度(mm)" prop="minAllowableThickness">
          <el-input v-model="form.minAllowableThickness" placeholder="请输入最小安全厚度(mm)"/>
        </el-form-item>
        <el-form-item label="检测周期" prop="defaultInspectionCycle">
          <el-select v-model="form.defaultInspectionCycle" placeholder="请选择检测周期">
            <el-option
              v-for="dict in dict.type.equip_inspection_cycle"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="控制措施" prop="controlMeasures">
          <el-input v-model="form.controlMeasures" type="textarea" placeholder="请输入内容"/>
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
  </div>
</template>

<script>
import { listBase, getBase, delBase, addBase, updateBase } from '@/api/anticorrosion/base'
import { listLedger } from '@/api/equip/ledger'

export default {
  name: 'Base',
  dicts: ['equip_levels', 'equip_ledger', 'equip_inspection_cycle', 'equip_type', 'equip_location', 'special_material'],
  data() {
    return {
      //表格展示列
      columns: [
        { key: 0, label: '主键ID', visible: true },
        { key: 1, label: '设备ID', visible: false },
        { key: 2, label: '设备名称', visible: true },
        { key: 3, label: '设备位号', visible: true },
        { key: 4, label: '设备管理级别', visible: true },
        { key: 5, label: '规格型号', visible: true },
        { key: 6, label: '易腐蚀部位', visible: true },
        { key: 7, label: '材质', visible: true },
        { key: 8, label: '介质', visible: true },
        { key: 9, label: '温度（℃）', visible: true },
        { key: 10, label: '压力(MPa)', visible: true },
        { key: 11, label: '设计厚度(mm)', visible: true },
        { key: 12, label: '最小安全厚度(mm)', visible: true },
        { key: 13, label: '检测周期', visible: true },
        { key: 14, label: '控制措施', visible: true },
        { key: 15, label: '备注', visible: false }
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
      // 防腐基础信息表格数据
      baseList: [],
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
        equipmentName: null,
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
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        equipmentId: [
          { required: true, message: '设备不能为空', trigger: 'change' }
        ]
      }
    }
  },
  created() {
    this.getList()
    this.getEquipList()
  },
  methods: {
    /** 查询防腐基础信息列表 */
    getList() {
      this.loading = true
      listBase(this.queryParams).then(response => {
        this.baseList = response.rows
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
        id: null,
        equipmentId: null,
        equipmentName: null,
        equipmentCode: null,
        equipLevels: null,
        specification: null,
        corrosionParts: null,
        material: null,
        medium: null,
        temperature: null,
        pressure: null,
        originalThickness: null,
        minAllowableThickness: null,
        defaultInspectionCycle: null,
        controlMeasures: null,
        remark: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        delFlag: null
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
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = '添加防腐基础信息'
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getBase(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = '修改防腐基础信息'
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateBase(this.form).then(response => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addBase(this.form).then(response => {
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
      const ids = row.id || this.ids
      this.$modal.confirm('是否确认删除防腐基础信息编号为"' + ids + '"的数据项？').then(function() {
        return delBase(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess('删除成功')
      }).catch(() => {
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('anticorrosion/base/export', {
        ...this.queryParams
      }, `base_${new Date().getTime()}.xlsx`)
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
      this.form.equipmentId = row.equipId
      this.form.equipmentName = row.equName
      this.form.equipmentCode = row.equCode
      this.form.equipLevels = row.equipLevels
      this.form.specification = row.specification
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
        this.form.equipmentName = selectedEquip.equName
        this.form.equipmentCode = selectedEquip.equCode
        this.form.equipLevels = selectedEquip.equipLevels
        this.form.specification = selectedEquip.specification
      }
    }
  }
}
</script>
