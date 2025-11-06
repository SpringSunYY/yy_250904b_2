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
          v-hasPermi="['patrol:item:add']"
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
          v-hasPermi="['patrol:item:edit']"
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
          v-hasPermi="['patrol:item:remove']"
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
          v-hasPermi="['patrol:item:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="itemList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="序号" align="center" v-if="columns[0].visible" prop="itemId"/>
      <el-table-column label="设备名称" :show-overflow-tooltip="true" align="center" v-if="columns[1].visible"
                       prop="equipName"
      />
      <el-table-column label="巡检项" :show-overflow-tooltip="true" align="center" v-if="columns[2].visible"
                       prop="itemName"
      />
      <el-table-column label="检查标准" :show-overflow-tooltip="true" align="center" v-if="columns[3].visible"
                       prop="standard"
      />
      <el-table-column label="备注" :show-overflow-tooltip="true" align="center" v-if="columns[4].visible"
                       prop="remark"
      />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['patrol:item:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['patrol:item:remove']"
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

    <!-- 添加或修改设备巡检项对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="900px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
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
        <el-form-item label="巡检项" prop="itemName">
          <el-input v-model="form.itemName" placeholder="请输入巡检项"/>
        </el-form-item>
        <el-form-item label="检查标准" prop="standard">
          <el-input v-model="form.standard" placeholder="请输入检查标准"/>
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
import { listItem, getItem, delItem, addItem, updateItem } from '@/api/patrol/item'
import { listLedger } from '@/api/equip/ledger'

export default {
  name: 'Item',
  dicts: ['equip_ledger', 'equip_type', 'equip_levels', 'equip_location'],
  data() {
    return {
      //表格展示列
      columns: [
        { key: 0, label: '序号', visible: true },
        { key: 1, label: '设备名称', visible: true },
        { key: 2, label: '巡检项', visible: true },
        { key: 3, label: '检查标准', visible: true },
        { key: 4, label: '备注', visible: false }
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
      // 设备巡检项表格数据
      itemList: [],
      // 设备列表
      equipList: [],
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
        equipName: null
      },
      // 设备查询参数
      equipQueryParams: {
        pageNum: 1,
        pageSize: 10,
        equName: undefined,
        equCode: undefined
      },
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
        equipId: [
          { required: true, message: '设备不能为空', trigger: 'change' }
        ],
        itemName: [
          { required: true, message: '巡检项不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  created() {
    this.getList()
    this.getEquipList()
  },
  methods: {
    /** 查询设备巡检项列表 */
    getList() {
      this.loading = true
      listItem(this.queryParams).then(response => {
        this.itemList = response.rows
        // 为每个项目添加设备位号信息
        this.itemList.forEach(item => {
          if (item.equipId) {
            const selectedEquip = this.equipList.find(equip => equip.equipId === item.equipId)
            if (selectedEquip) {
              item.equipCode = selectedEquip.equCode
            }
          }
        })
        this.total = response.total
        this.loading = false
      })
    },
    // 获取设备列表
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
    // 设备选择变化处理
    handleEquipChange(val) {
      if (val) {
        const selectedEquip = this.equipList.find(equip => equip.equipId === val)
        if (selectedEquip) {
          this.form.equipName = selectedEquip.equName
          this.form.equipCode = selectedEquip.equCode
        }
      } else {
        this.form.equipName = ''
        this.form.equipCode = ''
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
        itemId: null,
        equipId: null,
        equipName: null,
        equipCode: null,
        itemName: null,
        standard: null,
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
      this.ids = selection.map(item => item.itemId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = '添加设备巡检项'
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const itemId = row.itemId || this.ids
      getItem(itemId).then(response => {
        this.form = response.data
        // 如果是编辑操作且已有设备ID，则自动填充设备位号
        if (this.form.equipId) {
          this.$nextTick(() => {
            const selectedEquip = this.equipList.find(equip => equip.equipId === this.form.equipId)
            if (selectedEquip) {
              this.form.equipCode = selectedEquip.equCode
            }
          })
        }
        this.open = true
        this.title = '修改设备巡检项'
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          if (this.form.itemId != null) {
            updateItem(this.form).then(response => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addItem(this.form).then(response => {
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
      const itemIds = row.itemId || this.ids
      this.$modal.confirm('是否确认删除设备巡检项编号为"' + itemIds + '"的数据项？').then(function() {
        return delItem(itemIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess('删除成功')
      }).catch(() => {
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('patrol/item/export', {
        ...this.queryParams
      }, `item_${new Date().getTime()}.xlsx`)
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
