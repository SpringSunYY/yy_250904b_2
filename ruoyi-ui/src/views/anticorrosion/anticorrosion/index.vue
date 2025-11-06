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
      <el-form-item label="检测部门" prop="inspectionDept">
        <el-input
          v-model="queryParams.inspectionDept"
          placeholder="请输入检测部门"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="检测人" prop="inspector">
        <el-input
          v-model="queryParams.inspector"
          placeholder="请输入检测人"
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
          v-hasPermi="['anticorrosion:anticorrosion:add']"
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
          v-hasPermi="['anticorrosion:anticorrosion:edit']"
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
          v-hasPermi="['anticorrosion:anticorrosion:remove']"
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
          v-hasPermi="['anticorrosion:anticorrosion:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="anticorrosionList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="序号" align="center" v-if="columns[0].visible" prop="id"/>
      <el-table-column label="记录编号" :show-overflow-tooltip="true" align="center" v-if="columns[1].visible"
                       prop="recordNo"
      />
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
      <el-table-column label="检测日期" align="center" v-if="columns[5].visible" prop="inspectionDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.inspectionDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="检测部门" :show-overflow-tooltip="true" align="center" v-if="columns[6].visible"
                       prop="inspectionDept"
      />
      <el-table-column label="检测人" :show-overflow-tooltip="true" align="center" v-if="columns[7].visible"
                       prop="inspector"
      />
      <el-table-column label="测点1厚度(mm)" :show-overflow-tooltip="true" align="center" v-if="columns[8].visible"
                       prop="point1Thickness"
      />
      <el-table-column label="测点2厚度(mm)" :show-overflow-tooltip="true" align="center" v-if="columns[9].visible"
                       prop="point2Thickness"
      />
      <el-table-column label="测点3厚度(mm)" :show-overflow-tooltip="true" align="center" v-if="columns[10].visible"
                       prop="point3Thickness"
      />
      <el-table-column label="备注" :show-overflow-tooltip="true" align="center" v-if="columns[11].visible"
                       prop="remark"
      />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" fixed="right">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['anticorrosion:anticorrosion:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['anticorrosion:anticorrosion:remove']"
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

    <!-- 添加或修改防腐蚀检测对话框 -->
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
        <el-form-item label="检测日期" prop="inspectionDate">
          <el-date-picker clearable
                          v-model="form.inspectionDate"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择检测日期"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="检测部门" prop="inspectionId">
          <treeselect
            v-model="form.inspectionId"
            :options="deptOptions"
            :show-count="true"
            placeholder="请选择检测部门"
            :normalizer="normalizer"
          />
        </el-form-item>
        <el-form-item label="检测人" prop="inspectorIds">
          <el-row>
            <el-col :span="20">
              <el-select
                v-model="form.inspectorIds"
                placeholder="请选择检测人"
                multiple
                style="width: 100%"
              >
                <el-option
                  v-for="user in userList"
                  :key="user.userId"
                  :label="user.nickName"
                  :value="user.userId"
                >
                </el-option>
              </el-select>
            </el-col>
            <el-col :span="4">
              <el-button type="primary" @click="openUserSelector" style="margin-left: 10px;">选择人员</el-button>
            </el-col>
          </el-row>
        </el-form-item>
        <el-form-item label="测点1厚度(mm)" prop="point1Thickness">
          <el-input v-model="form.point1Thickness" placeholder="请输入测点1厚度(mm)"/>
        </el-form-item>
        <el-form-item label="测点2厚度(mm)" prop="point2Thickness">
          <el-input v-model="form.point2Thickness" placeholder="请输入测点2厚度(mm)"/>
        </el-form-item>
        <el-form-item label="测点3厚度(mm)" prop="point3Thickness">
          <el-input v-model="form.point3Thickness" placeholder="请输入测点3厚度(mm)"/>
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

    <!-- 用户选择对话框 -->
    <el-dialog title="选择用户" :visible.sync="userSelectorOpen" width="800px" append-to-body>
      <flow-user
        ref="flowUser"
        :select-values="form.inspectorIds"
        check-type="multiple"
        @handleUserSelect="handleUserSelect"
      >
      </flow-user>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="confirmUserSelector">确 定</el-button>
        <el-button @click="userSelectorOpen = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listAnticorrosion,
  getAnticorrosion,
  delAnticorrosion,
  addAnticorrosion,
  updateAnticorrosion
} from '@/api/anticorrosion/anticorrosion'
import { listLedger } from '@/api/equip/ledger'
import { treeselect as deptTreeSelect } from '@/api/system/dept'
import { listUser } from '@/api/system/user'
import Treeselect from '@riophae/vue-treeselect'
import '@riophae/vue-treeselect/dist/vue-treeselect.css'
import FlowUser from '@/components/flow/User'

export default {
  name: 'Anticorrosion',
  components: { Treeselect, FlowUser },
  dicts: ['equip_levels', 'equip_dept', 'equip_user', 'equip_ledger', 'equip_type', 'equip_location'],
  data() {
    return {
      //表格展示列
      columns: [
        { key: 0, label: '序号', visible: true },
        { key: 1, label: '记录编号', visible: true },
        { key: 2, label: '设备名称', visible: true },
        { key: 3, label: '设备位号', visible: true },
        { key: 4, label: '设备管理级别', visible: true },
        { key: 5, label: '检测日期', visible: true },
        { key: 6, label: '检测部门', visible: true },
        { key: 7, label: '检测人', visible: true },
        { key: 8, label: '测点1厚度(mm)', visible: true },
        { key: 9, label: '测点2厚度(mm)', visible: true },
        { key: 10, label: '测点3厚度(mm)', visible: true },
        { key: 11, label: '备注', visible: false }
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
      // 防腐蚀检测表格数据
      anticorrosionList: [],
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 是否显示设备选择弹出层
      equipSelectorOpen: false,
      // 是否显示用户选择弹出层
      userSelectorOpen: false,
      // 部门树选项
      deptOptions: [],
      // 用户列表
      userList: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        equipmentName: null,
        equipLevels: null,
        inspectionDept: null,
        inspector: null
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
      },
      // 部门树格式化
      normalizer: function(node) {
        return {
          id: node.id,
          label: node.label,
          children: node.children
        }
      }
    }
  },
  created() {
    this.getList()
    this.getEquipList()
    this.getDeptTree()
    this.getUserList()
  },
  methods: {
    /** 查询部门下拉树结构 */
    getDeptTree() {
      deptTreeSelect().then(response => {
        this.deptOptions = response.data
      })
    },
    /** 递归查找部门 */
    findDeptById(deptOptions, id) {
      for (let i = 0; i < deptOptions.length; i++) {
        const dept = deptOptions[i]
        if (dept.id === id) {
          return dept
        }
        if (dept.children) {
          const foundDept = this.findDeptById(dept.children, id)
          if (foundDept) {
            return foundDept
          }
        }
      }
      return null
    },
    /** 查询防腐蚀检测列表 */
    getList() {
      this.loading = true
      listAnticorrosion(this.queryParams).then(response => {
        this.anticorrosionList = response.rows
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
        recordNo: null,
        equipmentId: null,
        equipmentName: null,
        equipmentCode: null,
        equipLevels: null,
        inspectionDate: null,
        inspectionId: null,
        inspectionDept: null,
        inspectorIds: [],
        inspector: null,
        point1Thickness: null,
        point2Thickness: null,
        point3Thickness: null,
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
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = '添加防腐蚀检测'
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getAnticorrosion(id).then(response => {
        this.form = response.data
        // 确保inspectionId是数字类型
        if (this.form.inspectionId) {
          this.form.inspectionId = parseInt(this.form.inspectionId)
        }
        // 将检测人ID字符串转换为数组
        if (this.form.inspectorId) {
          this.form.inspectorIds = this.form.inspectorId.split(',').map(id => parseInt(id))
        } else {
          this.form.inspectorIds = []
        }
        this.open = true
        this.title = '修改防腐蚀检测'
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          // 处理检测部门名称
          if (this.form.inspectionId) {
            const selectedDept = this.findDeptById(this.deptOptions, this.form.inspectionId)
            if (selectedDept) {
              this.form.inspectionDept = selectedDept.label
            }
          }

          // 处理检测人数据
          if (this.form.inspectorIds && this.form.inspectorIds.length > 0) {
            // 获取选中用户的名称
            const selectedUsers = this.userList.filter(user =>
              this.form.inspectorIds.includes(user.userId)
            )
            this.form.inspector = selectedUsers.map(user => user.nickName).join(',')
            this.form.inspectorId = this.form.inspectorIds.join(',')
          } else {
            this.form.inspector = ''
            this.form.inspectorId = ''
          }

          if (this.form.id != null) {
            updateAnticorrosion(this.form).then(response => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addAnticorrosion(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除防腐蚀检测编号为"' + ids + '"的数据项？').then(function() {
        return delAnticorrosion(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess('删除成功')
      }).catch(() => {
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('anticorrosion/anticorrosion/export', {
        ...this.queryParams
      }, `anticorrosion_${new Date().getTime()}.xlsx`)
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

      // 自动填充检测部门信息
      if (row.departmentId && row.departmentName) {
        this.form.inspectionId = row.departmentId
        this.form.inspectionDept = row.departmentName
      }
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

        // 自动填充检测部门信息
        if (selectedEquip.departmentId && selectedEquip.departmentName) {
          this.form.inspectionId = selectedEquip.departmentId
          this.form.inspectionDept = selectedEquip.departmentName
        }
      }
    },

    /** 获取用户列表 */
    getUserList() {
      listUser({ pageSize: 1000 }).then(response => {
        this.userList = response.rows
      })
    },
    /** 打开用户选择器 */
    openUserSelector() {
      this.userSelectorOpen = true
    },
    /** 用户选择确认 */
    confirmUserSelector() {
      this.userSelectorOpen = false
    },
    /** 用户选中数据 */
    handleUserSelect(selection) {
      this.form.inspectorIds = selection.map(item => item.userId)
    }
  }
}
</script>

