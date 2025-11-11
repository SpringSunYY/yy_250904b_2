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
      <el-form-item label="设备位号" prop="equipCode">
        <el-input
          v-model="queryParams.equipCode"
          placeholder="请输入设备位号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="巡检人" prop="userName">
        <el-input
          v-model="queryParams.userName"
          placeholder="请输入巡检人"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable>
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
          v-hasPermi="['patrol:patrol:add']"
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
          v-hasPermi="['patrol:patrol:edit']"
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
          v-hasPermi="['patrol:patrol:remove']"
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
          v-hasPermi="['patrol:patrol:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="patrolList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="序号" align="center" prop="patrolId" v-if="columns[0].visible"/>
      <el-table-column label="巡检单号" align="center" prop="patrolNo" v-if="columns[1].visible"/>
      <el-table-column label="设备名称" align="center" prop="equipName" v-if="columns[2].visible"/>
      <el-table-column label="设备位号" align="center" prop="equipCode" v-if="columns[3].visible"/>
      <el-table-column label="巡检人" align="center" prop="userName" v-if="columns[4].visible"/>
      <el-table-column label="巡检时间" align="center" prop="patrolTime" width="180" v-if="columns[5].visible">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.patrolTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="巡检结果" align="center"prop="patrolResult" v-if="columns[6].visible">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.equip_patrol" :value="scope.row.patrolResult"/>
        </template>
      </el-table-column>
      <el-table-column label="问题照片" align="center"prop="problemImages" v-if="columns[7].visible" width="100">
        <template slot-scope="scope">
          <image-preview :src="scope.row.problemImages" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="问题描述" align="center"prop="problem" v-if="columns[8].visible">
        <template slot-scope="scope">
          <span>{{ scope.row.problem || '--' }}</span>
        </template>
      </el-table-column>
      <el-table-column label="处理措施" :show-overflow-tooltip="true" align="center" v-if="columns[9].visible"
                       prop="processMeasures"
      />
      <el-table-column label="处理后照片" align="center" v-if="columns[10].visible" prop="processImage" width="100">
        <template slot-scope="scope">
          <image-preview :src="scope.row.processImage" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="处理人" :show-overflow-tooltip="true" align="center" v-if="columns[11].visible"
                       prop="processUserName"
      />
      <el-table-column label="状态" align="center" prop="status" v-if="columns[12].visible">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.equip_repair_sratus" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" v-if="columns[13].visible"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['patrol:patrol:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['patrol:patrol:remove']"
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

    <!-- 添加或修改巡检记录对话框 -->
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
        <el-form-item label="巡检部门" prop="deptId">
          <el-select v-model="form.deptId" placeholder="请选择巡检部门" clearable @change="handleDeptChange">
            <el-option
              v-for="dept in deptList"
              :key="dept.deptId"
              :label="dept.deptName"
              :value="dept.deptId"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="巡检人" prop="userId">
          <el-select v-model="form.userId" placeholder="请选择巡检人" clearable filterable :disabled="!form.deptId">
            <el-option
              v-for="user in deptUserList"
              :key="user.userId"
              :label="user.userName"
              :value="user.userId"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="巡检时间" prop="patrolTime">
          <el-date-picker clearable
                          v-model="form.patrolTime"
                          type="datetime"
                          value-format="yyyy-MM-dd HH:mm:ss"
                          placeholder="请选择巡检时间"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="巡检结果" prop="patrolResult">
          <el-select v-model="form.patrolResult" placeholder="请选择巡检结果">
            <el-option
              v-for="dict in dict.type.equip_patrol"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="问题照片" prop="problemImages">
          <image-upload v-model="form.problemImages"/>
        </el-form-item>
        <el-form-item label="问题描述" prop="problem">
          <el-input v-model="form.problem" type="textarea" placeholder="请输入问题描述"/>
        </el-form-item>
        <el-form-item label="处理措施" prop="processMeasures">
          <el-input v-model="form.processMeasures" type="textarea" placeholder="请输入内容"/>
        </el-form-item>
        <el-form-item label="处理后照片" prop="processImage">
          <image-upload v-model="form.processImage"/>
        </el-form-item>
        <el-form-item label="处理人" prop="processUserId">
          <el-select
            v-model="form.processUserId"
            placeholder="请选择处理人"
            filterable
            remote
          >
            <el-option
              v-for="item in processUserList"
              :key="item.userId"
              :label="item.userName"
              :value="item.userId"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-divider content-position="center">巡检记录明细信息</el-divider>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAddEquipPatrolDetail">添加
            </el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" icon="el-icon-delete" size="mini" @click="handleDeleteEquipPatrolDetail">删除
            </el-button>
          </el-col>
        </el-row>
        <el-table :data="equipPatrolDetailList" :row-class-name="rowEquipPatrolDetailIndex"
                  @selection-change="handleEquipPatrolDetailSelectionChange" ref="equipPatrolDetail"
        >
          <el-table-column type="selection" width="50" align="center"/>
          <el-table-column label="序号" align="center" prop="index" width="50"/>
          <el-table-column label="巡检项" prop="itemName" width="150">
            <template slot-scope="scope">
              <el-select
                v-model="scope.row.itemName"
                placeholder="请选择巡检项"
                clearable
                filterable
                @change="handleItemChange(scope.row)"
              >
                <el-option
                  v-for="item in filteredEquipItemList"
                  :key="item.itemId"
                  :label="item.itemName"
                  :value="item.itemName"
                >
                </el-option>
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="问题描述" prop="problem" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.problem" placeholder="请输入问题描述"/>
            </template>
          </el-table-column>
          <el-table-column label="附件" prop="images" width="150">
            <template slot-scope="scope">
              <el-upload
                class="upload-demo"
                action="#"
                :auto-upload="false"
                :file-list="scope.row.fileList || []"
                :on-change="(file, fileList) => handleFileChange(file, fileList, scope.$index)"
                :on-remove="(file, fileList) => handleFileRemove(file, fileList, scope.$index)"
                multiple
                list-type="picture"
                :limit="3"
              >
                <el-button size="mini" type="primary">点击上传</el-button>
              </el-upload>
            </template>
          </el-table-column>
          <el-table-column label="备注" prop="remark" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.remark" placeholder="请输入备注"/>
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
  </div>
</template>

<script>
import { listPatrol, getPatrol, delPatrol, addPatrol, updatePatrol } from '@/api/patrol/patrol'
import { listLedger } from '@/api/equip/ledger'
import { listUser } from '@/api/system/user'
import { listDept } from '@/api/system/dept'
import { listItem } from '@/api/patrol/item'

export default {
  name: 'Patrol',
  dicts: ['equip_user', 'equip_patrol', 'equip_repair_sratus', 'equip_ledger', 'sys_process_category', 'equip_type', 'equip_levels', 'equip_location'],
  data() {
    return {
      processUserList: [],
      //表格展示列
      columns: [
        { key: 0, label: '序号', visible: true },
        { key: 1, label: '巡检单号',visible: true },
        { key: 2, label: '设备名称',visible: true },
        { key: 3, label: '设备位号',visible: true },
        { key: 4, label: '巡检人',visible: true },
        { key: 5, label: '巡检时间',visible: true },
        { key: 6, label: '巡检结果',visible: true },
        { key: 7, label: '问题照片',visible: true },
        { key: 8, label: '问题描述',visible: true },
        { key: 9, label: '处理措施',visible: true },
        { key: 10, label: '处理后照片',visible: true },
        { key: 11, label: '处理人',visible: true },
        { key: 12, label: '状态',visible: true },
        { key: 13, label: '备注',visible: false }
      ],
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 子表选中数据
      checkedEquipPatrolDetail: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 巡检记录表格数据
      patrolList: [],
      // 设备列表
      equipList: [],
      // 部门列表
      deptList: [],
      // 部门内用户列表
      deptUserList: [],
      // 设备巡检项列表
      equipItemList: [],
      // 巡检记录明细表格数据
      equipPatrolDetailList: [],
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
        equipCode: null,
        userName: null,
        status: null
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
        deptId: [
          { required: true, message: '巡检部门不能为空', trigger: 'change' }
        ],
        userId: [
          { required: true, message: '巡检人不能为空', trigger: 'change' }
        ],
        patrolTime: [
          { required: true, message: '巡检时间不能为空', trigger: 'change' }
        ]
      }
    }
  },
  computed: {
    // 过滤后的设备巡检项列表
    filteredEquipItemList() {
      if (this.form.equipId) {
        return this.equipItemList.filter(item => item.equipId === this.form.equipId)
      }
      return []
    }
  },
  created() {
    this.getList()
    this.getEquipList()
    this.getDeptList()
    this.getProcessUserList()
    // 调试信息：查看字典数据
    this.$nextTick(() => {
      console.log('equip_patrol字典数据:', this.dict.type.equip_patrol)
    })
  },
  methods: {
    getProcessUserList() {
      listUser({ pageNum: 1, pageSize: 1000 }).then(response => {
        this.processUserList = response.rows
      })
    },
    /** 查询巡检记录列表 */
    getList() {
      this.loading = true
      listPatrol(this.queryParams).then(response => {
        this.patrolList = response.rows
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
    // 获取部门列表
    getDeptList() {
      listDept().then(response => {
        this.deptList = response.data
      })
    },
    // 设备选择变化处理
    handleEquipChange(val) {
      if (val) {
        const selectedEquip = this.equipList.find(equip => equip.equipId === val)
        if (selectedEquip) {
          this.form.equipName = selectedEquip.equName
          this.form.equipCode = selectedEquip.equCode

          // 获取该设备的巡检项
          listItem({ equipId: val }).then(response => {
            this.equipItemList = response.rows
          })
        }
      } else {
        this.form.equipName = ''
        this.form.equipCode = ''
        this.equipItemList = []
      }
    },
    // 巡检项选择变化处理
    handleItemChange(row) {
      if (row.itemName) {
        // 根据选择的巡检项名称查找对应的巡检项信息
        const selectedItem = this.equipItemList.find(item => item.itemName === row.itemName && item.equipId === this.form.equipId)
        if (selectedItem) {
          row.standard = selectedItem.standard
          row.itemId = selectedItem.itemId
        }
      } else {
        row.standard = ''
        row.itemId = null
      }
    },
    // 部门选择变化处理
    handleDeptChange(val) {
      if (val) {
        // 获取部门下的用户
        listUser({ deptId: val }).then(response => {
          this.deptUserList = response.rows
        })
        // 清空已选择的用户
        this.form.userId = null
        this.form.userName = ''
      } else {
        // 清空部门内用户列表
        this.deptUserList = []
        // 清空已选择的用户
        this.form.userId = null
        this.form.userName = ''
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
        patrolId: null,
        patrolNo: null,
        equipId: null,
        equipName: null,
        equipCode: null,
        deptId: null,
        userId: null,
        userName: null,
        patrolTime: null,
        patrolResult: null,
        problemImages: null,
        problem: null,
        processInstanceId: null,
        status: null,
        remark: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null
      }
      this.deptUserList = []
      this.equipItemList = []
      this.equipPatrolDetailList = []
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
      this.ids = selection.map(item => item.patrolId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      // 自动生成巡检单号
      const now = new Date()
      const year = now.getFullYear()
      const month = String(now.getMonth() + 1).padStart(2, '0')
      const day = String(now.getDate()).padStart(2, '0')
      const hours = String(now.getHours()).padStart(2, '0')
      const minutes = String(now.getMinutes()).padStart(2, '0')
      const seconds = String(now.getSeconds()).padStart(2, '0')
      this.form.patrolNo = `XJ${year}${month}${day}${hours}${minutes}${seconds}`
      this.open = true
      this.title = '添加巡检记录'
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const patrolId = row.patrolId || this.ids
      getPatrol(patrolId).then(response => {
        this.form = response.data

        // 如果是编辑操作且已有设备ID，则自动填充设备名称和设备位号
        if (this.form.equipId) {
          this.$nextTick(() => {
            const selectedEquip = this.equipList.find(equip => equip.equipId === this.form.equipId)
            if (selectedEquip) {
              this.form.equipName = selectedEquip.equName
              this.form.equipCode = selectedEquip.equCode

              // 获取该设备的巡检项
              listItem({ equipId: this.form.equipId }).then(response => {
                this.equipItemList = response.rows
              })
            }
          })
        }
        // 如果是编辑操作且已有部门ID，则获取部门下的用户
        if (this.form.deptId) {
          this.$nextTick(() => {
            listUser({ deptId: this.form.deptId }).then(response => {
              this.deptUserList = response.rows
              // 如果已有用户ID，则自动填充用户名
              if (this.form.userId) {
                const selectedUser = this.deptUserList.find(user => user.userId === this.form.userId)
                if (selectedUser) {
                  this.form.userName = selectedUser.userName
                }
              }
            })
          })
        }
        // 确保巡检明细中的result字段存在
        this.equipPatrolDetailList = (response.data.equipPatrolDetailList || []).map(item => {
          return {
            ...item,
            result: item.result || ''
          }
        })
        this.open = true
        this.title = '修改巡检记录'
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          // 设置用户名
          if (this.form.userId) {
            const selectedUser = this.deptUserList.find(user => user.userId === this.form.userId)
            if (selectedUser) {
              this.form.userName = selectedUser.userName
            }
          }

          // 确保设备名称和设备位号被正确设置
          if (this.form.equipId) {
            const selectedEquip = this.equipList.find(equip => equip.equipId === this.form.equipId)
            if (selectedEquip) {
              this.form.equipName = selectedEquip.equName
              this.form.equipCode = selectedEquip.equCode
            }
          }

          // 确保巡检明细数据正确传递
          this.form.equipPatrolDetailList = this.equipPatrolDetailList

          // 调试信息：打印完整的表单数据
          console.log('提交表单前的完整数据:', JSON.stringify(this.form, null, 2))

          if (this.form.patrolId != null) {
            updatePatrol(this.form).then(response => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addPatrol(this.form).then(response => {
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
      const patrolIds = row.patrolId || this.ids
      this.$modal.confirm('是否确认删除巡检记录编号为"' + patrolIds + '"的数据项？').then(function() {
        return delPatrol(patrolIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess('删除成功')
      }).catch(() => {
      })
    },
    /** 巡检记录明细序号 */
    rowEquipPatrolDetailIndex({ row, rowIndex }) {
      row.index = rowIndex + 1
    },
    /** 巡检记录明细添加按钮操作 */
    handleAddEquipPatrolDetail() {
      let obj = {}
      obj.itemName = ''
      obj.standard = ''
      obj.result = ''  // 确保初始化巡检结果字段
      obj.problem = ''
      obj.images = ''
      obj.remark = ''
      obj.itemId = null
      // 调试信息
      console.log('添加新的巡检明细项:', obj)
      this.equipPatrolDetailList.push(obj)
    },
    /** 巡检记录明细删除按钮操作 */
    handleDeleteEquipPatrolDetail() {
      if (this.checkedEquipPatrolDetail.length == 0) {
        this.$modal.msgError('请先选择要删除的巡检记录明细数据')
      } else {
        const equipPatrolDetailList = this.equipPatrolDetailList
        const checkedEquipPatrolDetail = this.checkedEquipPatrolDetail
        this.equipPatrolDetailList = equipPatrolDetailList.filter(function(item) {
          return checkedEquipPatrolDetail.indexOf(item.index) == -1
        })
      }
    },
    /** 复选框选中数据 */
    handleEquipPatrolDetailSelectionChange(selection) {
      this.checkedEquipPatrolDetail = selection.map(item => item.index)
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('patrol/patrol/export', {
        ...this.queryParams
      }, `patrol_${new Date().getTime()}.xlsx`)
    },
    // 处理文件上传变化
    handleFileChange(file, fileList, index) {
      // 为当前行创建fileList属性（如果不存在）
      if (!this.equipPatrolDetailList[index].fileList) {
        this.$set(this.equipPatrolDetailList[index], 'fileList', [])
      }
      // 更新文件列表
      this.equipPatrolDetailList[index].fileList = fileList
    },
    // 处理文件移除
    handleFileRemove(file, fileList, index) {
      // 更新文件列表
      if (index >= 0 && index < this.equipPatrolDetailList.length) {
        if (this.equipPatrolDetailList[index]) {
          this.equipPatrolDetailList[index].fileList = fileList
        }
      }
    },
    // 处理上传附件
    handleUpload(row) {
      this.$message.info('上传附件功能待实现')
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

      // 获取该设备的巡检项
      listItem({ equipId: row.equipId }).then(response => {
        this.equipItemList = response.rows
      })
    },
    /** 打开设备选择器 */
    openEquipSelector() {
      this.equipSelectorOpen = true
      this.resetEquipQuery()
    }
  }
}
</script>
