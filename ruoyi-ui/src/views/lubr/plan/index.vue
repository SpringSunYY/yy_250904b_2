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
      <el-form-item label="润滑周期" prop="lubInterval">
        <el-select v-model="queryParams.lubInterval" placeholder="请选择润滑周期" clearable>
          <el-option
            v-for="dict in dict.type.equip_lub_interval"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="油类别" prop="lubCategory">
        <el-select v-model="queryParams.lubCategory" placeholder="请选择润滑油类别" clearable>
          <el-option
            v-for="dict in dict.type.equip_lub_category"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="油牌号" prop="lubGrade">
        <el-input
          v-model="queryParams.lubGrade"
          placeholder="请输入润滑油牌号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="加注方式" prop="lubMethod">
        <el-select v-model="queryParams.lubMethod" placeholder="请选择加注方式" clearable>
          <el-option
            v-for="dict in dict.type.equip_lub_method"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="负责人" prop="responsibleName">
        <el-input
          v-model="queryParams.responsibleName"
          placeholder="请输入负责人"
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
          v-hasPermi="['lubr:plan:add']"
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
          v-hasPermi="['lubr:plan:edit']"
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
          v-hasPermi="['lubr:plan:remove']"
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
          v-hasPermi="['lubr:plan:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="planList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="润滑计划ID" align="center" v-if="columns[0].visible" prop="planId"/>
      <el-table-column label="计划编号" :show-overflow-tooltip="true" align="center" v-if="columns[1].visible"
                       prop="planNo"
      />
      <el-table-column label="设备ID" align="center" v-if="columns[2].visible" prop="equipId"/>
      <el-table-column label="设备名称" :show-overflow-tooltip="true" align="center" v-if="columns[3].visible"
                       prop="equipName"
      />
      <el-table-column label="设备位号" :show-overflow-tooltip="true" align="center" v-if="columns[4].visible"
                       prop="equCode"
      />
      <el-table-column label="管理级别" :show-overflow-tooltip="true" align="center" v-if="columns[5].visible"
                       prop="equipLevels"
      >
        <template slot-scope="scope">
          <dict-tag :options="dict.type.equip_levels" :value="scope.row.equipLevels"/>
        </template>
      </el-table-column>
      <el-table-column label="润滑部位" :show-overflow-tooltip="true" align="center" v-if="columns[6].visible"
                       prop="lubLocation"
      />
      <el-table-column label="润滑点数" :show-overflow-tooltip="true" align="center" v-if="columns[7].visible"
                       prop="lubPoints"
      />
      <el-table-column label="润滑周期" align="center" v-if="columns[8].visible" prop="lubInterval">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.equip_lub_interval" :value="scope.row.lubInterval"/>
        </template>
      </el-table-column>
      <el-table-column label="润滑油类别" align="center" v-if="columns[9].visible" prop="lubCategory">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.equip_lub_category" :value="scope.row.lubCategory"/>
        </template>
      </el-table-column>
      <el-table-column label="润滑油牌号" :show-overflow-tooltip="true" align="center" v-if="columns[10].visible"
                       prop="lubGrade"
      />
      <el-table-column label="加注方式" align="center" v-if="columns[11].visible" prop="lubMethod">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.equip_lub_method" :value="scope.row.lubMethod"/>
        </template>
      </el-table-column>
      <el-table-column label="标准数量(L)" :show-overflow-tooltip="true" align="center" v-if="columns[12].visible"
                       prop="standardQuantity"
      />
      <el-table-column label="负责人ID" align="center" v-if="columns[13].visible" prop="responsibleId">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.equip_user" :value="scope.row.responsibleId"/>
        </template>
      </el-table-column>
      <el-table-column label="负责人" :show-overflow-tooltip="true" align="center" v-if="columns[14].visible"
                       prop="responsibleName"
      />
<!--      <el-table-column label="申请部门ID" :show-overflow-tooltip="true" align="center" v-if="columns[15].visible"-->
<!--                       prop="deptId"-->
<!--      />-->
<!--      <el-table-column label="申请部门" :show-overflow-tooltip="true" align="center" v-if="columns[16].visible"-->
<!--                       prop="deptName"-->
<!--      />-->
<!--      <el-table-column label="申请人ID" :show-overflow-tooltip="true" align="center" v-if="columns[17].visible"-->
<!--                       prop="applyUserId"-->
<!--      />-->
<!--      <el-table-column label="申请人" :show-overflow-tooltip="true" align="center" v-if="columns[18].visible"-->
<!--                       prop="applyUserName"-->
<!--      />-->
<!--      <el-table-column label="任务ID" :show-overflow-tooltip="true" align="center" v-if="columns[19].visible"-->
<!--                       prop="taskId"-->
<!--      />-->
<!--      <el-table-column label="流程实例ID" align="center" v-if="columns[20].visible" prop="processInstanceId">-->
<!--        <template slot-scope="scope">-->
<!--          <dict-tag :options="dict.type.sys_process_category" :value="scope.row.processInstanceId"/>-->
<!--        </template>-->
<!--      </el-table-column>-->
<!--      <el-table-column label="流程自定义ID" :show-overflow-tooltip="true" align="center" v-if="columns[21].visible"-->
<!--                       prop="deployId"-->
<!--      />-->
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
            v-hasPermi="['lubr:plan:edit']"
          >修改
          </el-button>
<!--          <el-button-->
<!--            size="mini"-->
<!--            type="text"-->
<!--            icon="el-icon-view"-->
<!--            @click="handleProcess(scope.row)"-->
<!--            v-hasPermi="['lubr:plan:list']"-->
<!--          >查看流程-->
<!--          </el-button>-->
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['lubr:plan:remove']"
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

    <!-- 添加或修改润滑计划对话框 -->
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
        <el-form-item label="设备位号" prop="equCode">
          <el-input v-model="form.equCode" placeholder="设备位号" disabled/>
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
        <el-form-item label="润滑部位" prop="lubLocation">
          <el-input v-model="form.lubLocation" placeholder="请输入润滑部位"/>
        </el-form-item>
        <el-form-item label="润滑点数" prop="lubPoints">
          <el-input v-model="form.lubPoints" placeholder="请输入润滑点数"/>
        </el-form-item>
        <el-form-item label="润滑周期" prop="lubInterval">
          <el-select v-model="form.lubInterval" placeholder="请选择润滑周期">
            <el-option
              v-for="dict in dict.type.equip_lub_interval"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="润滑油类别" prop="lubCategory">
          <el-select v-model="form.lubCategory" placeholder="请选择润滑油类别">
            <el-option
              v-for="dict in dict.type.equip_lub_category"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="润滑油牌号" prop="lubGrade">
          <el-input v-model="form.lubGrade" placeholder="请输入润滑油牌号"/>
        </el-form-item>
        <el-form-item label="加注方式" prop="lubMethod">
          <el-select v-model="form.lubMethod" placeholder="请选择加注方式">
            <el-option
              v-for="dict in dict.type.equip_lub_method"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="标准数量(L)" prop="standardQuantity">
          <el-input v-model="form.standardQuantity" placeholder="请输入标准数量(L)"/>
        </el-form-item>
        <el-form-item label="负责人" prop="responsibleId">
          <el-row>
            <el-col :span="20">
              <el-select v-model="form.responsibleId" placeholder="请选择负责人" style="width: 100%">
                <el-option
                  v-for="user in userList"
                  :key="user.userId"
                  :label="user.nickName"
                  :value="user.userId"
                >
                  <span style="float: left">{{ user.nickName }}</span>
                  <span style="float: right; color: #8492a6; font-size: 13px">{{ user.userName }}</span>
                </el-option>
              </el-select>
            </el-col>
            <el-col :span="4">
              <el-button type="primary" @click="openUserSelector" style="margin-left: 10px;">选择人员</el-button>
            </el-col>
          </el-row>
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
    <el-dialog title="选择人员" :visible.sync="userSelectorOpen" width="800px" append-to-body>
      <el-form :model="userQueryParams" ref="userQueryForm" size="small" :inline="true" label-width="68px">
        <el-form-item label="用户名称">
          <el-input
            v-model="userQueryParams.userName"
            placeholder="请输入用户名称"
            clearable
            @keyup.enter.native="handleUserQuery"
          />
        </el-form-item>
        <el-form-item label="用户昵称">
          <el-input
            v-model="userQueryParams.nickName"
            placeholder="请输入用户昵称"
            clearable
            @keyup.enter.native="handleUserQuery"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleUserQuery">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetUserQuery">重置</el-button>
        </el-form-item>
      </el-form>

      <el-table
        v-loading="userLoading"
        :data="userListSelector"
        @row-dblclick="handleSelectUser"
        max-height="400"
      >
        <el-table-column label="用户名称" align="center" prop="userName"/>
        <el-table-column label="用户昵称" align="center" prop="nickName"/>
        <el-table-column label="部门" align="center" prop="dept.deptName"/>
        <el-table-column label="手机号码" align="center" prop="phonenumber"/>
        <el-table-column label="状态" align="center" prop="status">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.sys_normal_disable" :value="scope.row.status"/>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-check"
              @click="handleSelectUser(scope.row)"
            >选择
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <pagination
        v-show="userTotal>0"
        :total="userTotal"
        :page.sync="userQueryParams.pageNum"
        :limit.sync="userQueryParams.pageSize"
        @pagination="getUserList"
      />

      <div slot="footer" class="dialog-footer">
        <el-button @click="userSelectorOpen = false">关 闭</el-button>
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
import { listPlan, getPlan, delPlan, addPlan, updatePlan } from '@/api/lubr/plan'
import { listLedger } from '@/api/equip/ledger'
import { listUser } from '@/api/system/user'
import TaskDetail from '@/components/TaskDetail/index.vue'

export default {
  name: 'Plan',
  components: { TaskDetail },
  dicts: ['sys_normal_disable', 'equip_user', 'equip_repair_sratus', 'equip_lub_method', 'equip_lub_category', 'equip_ledger', 'equip_lub_interval', 'sys_process_category', 'equip_levels', 'equip_type', 'equip_location'],
  data() {
    return {
      //表格展示列
      columns: [
        { key: 0, label: '润滑计划ID', visible: true },
        { key: 1, label: '计划编号', visible: true },
        { key: 2, label: '设备ID', visible: false },
        { key: 3, label: '设备名称', visible: true },
        { key: 4, label: '设备位号', visible: true },
        { key: 5, label: '管理级别', visible: true },
        { key: 6, label: '润滑部位', visible: true },
        { key: 7, label: '润滑点数', visible: true },
        { key: 8, label: '润滑周期', visible: true },
        { key: 9, label: '润滑油类别', visible: true },
        { key: 10, label: '润滑油牌号', visible: true },
        { key: 11, label: '加注方式', visible: true },
        { key: 12, label: '标准数量(L)', visible: true },
        { key: 13, label: '负责人ID', visible: false },
        { key: 14, label: '负责人', visible: true },
        // { key: 15, label: '申请部门ID', visible: false },
        // { key: 16, label: '申请部门', visible: false },
        // { key: 17, label: '申请人ID', visible: false },
        // { key: 18, label: '申请人', visible: false },
        // { key: 19, label: '任务ID', visible: false },
        // { key: 20, label: '流程实例ID', visible: false },
        // { key: 21, label: '流程自定义ID', visible: false },
        { key: 15, label: '备注', visible: false }
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
      // 润滑计划表格数据
      planList: [],
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 是否显示设备选择弹出层
      equipSelectorOpen: false,
      // 是否显示用户选择弹出层
      userSelectorOpen: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        equipName: null,
        lubInterval: null,
        lubCategory: null,
        lubGrade: null,
        lubMethod: null,
        responsibleName: null
      },
      // 设备查询参数
      equipQueryParams: {
        pageNum: 1,
        pageSize: 10,
        equName: undefined,
        equCode: undefined
      },
      // 用户查询参数
      userQueryParams: {
        pageNum: 1,
        pageSize: 10,
        userName: undefined,
        nickName: undefined
      },
      // 设备列表
      equipList: [],
      // 设备选择器列表
      equipListSelector: [],
      // 设备总数
      equipTotal: 0,
      // 设备加载
      equipLoading: false,
      // 用户列表
      userList: [],
      // 用户选择器列表
      userListSelector: [],
      // 用户总数
      userTotal: 0,
      // 用户加载
      userLoading: false,
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        equipId: [
          { required: true, message: '设备不能为空', trigger: 'change' }
        ]
      }
    }
  },
  created() {
    this.getList()
    this.getEquipList()
    this.getUserList()
  },
  methods: {
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
    /** 查询润滑计划列表 */
    getList() {
      this.loading = true
      listPlan(this.queryParams).then(response => {
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
        equipId: null,
        equipName: null,
        equCode: null,
        equipLevels: null,
        lubLocation: null,
        lubPoints: null,
        lubInterval: null,
        lubCategory: null,
        lubGrade: null,
        lubMethod: null,
        standardQuantity: null,
        responsibleId: null,
        responsibleName: null,
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
      this.title = '添加润滑计划'
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const planId = row.planId || this.ids
      getPlan(planId).then(response => {
        this.form = response.data
        this.open = true
        this.title = '修改润滑计划'
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs['form'].validate(valid => {
        if (valid) {
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
      this.$modal.confirm('是否确认删除润滑计划编号为"' + planIds + '"的数据项？').then(function() {
        return delPlan(planIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess('删除成功')
      }).catch(() => {
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('lubr/plan/export', {
        ...this.queryParams
      }, `plan_${new Date().getTime()}.xlsx`)
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
    },
    /** 获取用户列表 */
    getUserList() {
      this.userLoading = true
      listUser(this.userQueryParams).then(response => {
        this.userListSelector = response.rows
        this.userTotal = response.total
        this.userLoading = false

        // 同时更新下拉框中的用户列表
        if (!this.userQueryParams.userName && !this.userQueryParams.nickName) {
          this.userList = response.rows
        }
      })
    },
    /** 用户搜索 */
    handleUserQuery() {
      this.userQueryParams.pageNum = 1
      this.getUserList()
    },
    /** 重置用户搜索 */
    resetUserQuery() {
      this.resetForm('userQueryForm')
      this.handleUserQuery()
    },
    /** 选择用户 */
    handleSelectUser(row) {
      this.form.responsibleId = row.userId
      this.form.responsibleName = row.nickName
      this.userSelectorOpen = false
    },
    /** 打开用户选择器 */
    openUserSelector() {
      this.userSelectorOpen = true
      this.resetUserQuery()
    }
  }
}
</script>
