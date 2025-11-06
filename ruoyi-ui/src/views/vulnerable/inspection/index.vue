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
      <el-form-item label="易损部位状态" prop="vulnerablePartStatus">
        <el-select v-model="queryParams.vulnerablePartStatus" placeholder="请选择易损部位状态" clearable>
          <el-option
            v-for="dict in dict.type.vulnerable__status"
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
          v-hasPermi="['vulnerable:inspection:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['vulnerable:inspection:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['vulnerable:inspection:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['vulnerable:inspection:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="inspectionList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" v-if="columns[0].visible" prop="id" />
        <el-table-column label="检查编号" :show-overflow-tooltip="true" align="center" v-if="columns[1].visible" prop="inspectionNumber" />
        <el-table-column label="易损编号" :show-overflow-tooltip="true" align="center" v-if="columns[2].visible" prop="vulnerableNumber" />
        <el-table-column label="设备名称" :show-overflow-tooltip="true" align="center" v-if="columns[3].visible" prop="equipmentName" />
        <el-table-column label="检查日期" align="center" v-if="columns[4].visible" prop="inspectionDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.inspectionDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
        <el-table-column label="检查人" :show-overflow-tooltip="true" align="center" v-if="columns[5].visible" prop="inspector" />
        <el-table-column label="检查照片" align="center" v-if="columns[6].visible" prop="inspectionPhotos" width="100">
        <template slot-scope="scope">
          <image-preview :src="scope.row.inspectionPhotos" :width="50" :height="50"/>
        </template>
      </el-table-column>
        <el-table-column label="易损部位状态" align="center" v-if="columns[7].visible" prop="vulnerablePartStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.vulnerable__status" :value="scope.row.vulnerablePartStatus"/>
        </template>
      </el-table-column>
        <el-table-column label="问题描述" :show-overflow-tooltip="true" align="center" v-if="columns[8].visible" prop="problemDescription" />
        <el-table-column label="现场照片" align="center" v-if="columns[9].visible" prop="sitePhotos" width="100">
        <template slot-scope="scope">
          <image-preview :src="scope.row.sitePhotos" :width="50" :height="50"/>
        </template>
      </el-table-column>
        <el-table-column label="处理措施" :show-overflow-tooltip="true" align="center" v-if="columns[10].visible" prop="handlingMeasures" />
        <el-table-column label="处理人" :show-overflow-tooltip="true" align="center" v-if="columns[11].visible" prop="handler" />
        <el-table-column label="更换部件" :show-overflow-tooltip="true" align="center" v-if="columns[12].visible" prop="replacedParts" />
        <el-table-column label="更换后照片" align="center" v-if="columns[13].visible" prop="afterReplacementPhotos" width="100">
        <template slot-scope="scope">
          <image-preview :src="scope.row.afterReplacementPhotos" :width="50" :height="50"/>
        </template>
      </el-table-column>
        <el-table-column label="备注" :show-overflow-tooltip="true" align="center" v-if="columns[14].visible" prop="remarks" />
        <el-table-column label="创建时间" align="center" v-if="columns[15].visible" prop="createdTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createdTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width" fixed="right">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['vulnerable:inspection:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['vulnerable:inspection:remove']"
          >删除</el-button>
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

    <!-- 添加或修改易损设备检查对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="900px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="易损编号" prop="vulnerableNumber">
          <el-row>
            <el-col :span="20">
              <el-select
                v-model="form.vulnerableNumbers"
                placeholder="请选择易损设备"
                @change="handleVulnerableChange"
                style="width: 100%"
                multiple
              >
                <el-option
                  v-for="item in vulnerableList"
                  :key="item.vulnerableNumber"
                  :label="item.vulnerableNumber"
                  :value="item.vulnerableNumber"
                >
                  <span style="float: left">{{ item.vulnerableNumber }}</span>
                  <span style="float: right; color: #8492a6; font-size: 13px">{{ item.equipmentName }}</span>
                </el-option>
              </el-select>
            </el-col>
            <el-col :span="4">
              <el-button type="primary" @click="openVulnerableSelector" style="margin-left: 10px;">选择</el-button>
            </el-col>
          </el-row>
        </el-form-item>
        <el-form-item label="设备名称" prop="equipmentName">
          <el-input v-model="form.equipmentName" placeholder="设备名称" disabled/>
        </el-form-item>
        <el-form-item label="检查日期" prop="inspectionDate">
          <el-date-picker clearable
            v-model="form.inspectionDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择检查日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="检查人" prop="inspectors">
          <el-row>
            <el-col :span="20">
              <el-select
                v-model="form.inspectors"
                placeholder="请选择检查人"
                style="width: 100%"
                multiple
                filterable
                remote
                :remote-method="queryUsers"
                :loading="userLoading"
              >
                <el-option
                  v-for="item in userList"
                  :key="item.userId"
                  :label="item.nickName"
                  :value="item.userId"
                >
                  <span style="float: left">{{ item.nickName }}</span>
                  <span style="float: right; color: #8492a6; font-size: 13px">{{ item.dept.deptName }}</span>
                </el-option>
              </el-select>
            </el-col>
            <el-col :span="4">
              <el-button type="primary" @click="openUserSelector" style="margin-left: 10px;">选择</el-button>
            </el-col>
          </el-row>
        </el-form-item>
        <el-form-item label="检查照片" prop="inspectionPhotos">
          <image-upload v-model="form.inspectionPhotos"/>
        </el-form-item>
        <el-form-item label="易损部位状态" prop="vulnerablePartStatus">
          <el-select v-model="form.vulnerablePartStatus" placeholder="请选择易损部位状态">
            <el-option
              v-for="dict in dict.type.vulnerable__status"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="问题描述" prop="problemDescription">
          <el-input v-model="form.problemDescription" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="现场照片" prop="sitePhotos">
          <image-upload v-model="form.sitePhotos"/>
        </el-form-item>
        <el-form-item label="处理措施" prop="handlingMeasures">
          <el-input v-model="form.handlingMeasures" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="处理人" prop="handlers">
          <el-row>
            <el-col :span="20">
              <el-select
                v-model="form.handlers"
                placeholder="请选择处理人"
                style="width: 100%"
                multiple
                filterable
                remote
                :remote-method="queryUsers"
                :loading="userLoading"
              >
                <el-option
                  v-for="item in userList"
                  :key="item.userId"
                  :label="item.nickName"
                  :value="item.userId"
                >
                  <span style="float: left">{{ item.nickName }}</span>
                  <span style="float: right; color: #8492a6; font-size: 13px">{{ item.dept.deptName }}</span>
                </el-option>
              </el-select>
            </el-col>
            <el-col :span="4">
              <el-button type="primary" @click="openUserSelectorForHandler" style="margin-left: 10px;">选择</el-button>
            </el-col>
          </el-row>
        </el-form-item>
        <el-form-item label="更换部件" prop="replacedParts">
          <el-input v-model="form.replacedParts" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="更换后照片" prop="afterReplacementPhotos">
          <image-upload v-model="form.afterReplacementPhotos"/>
        </el-form-item>
        <el-form-item label="备注" prop="remarks">
          <el-input v-model="form.remarks" type="textarea" placeholder="请输入内容" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 易损设备选择对话框 -->
    <el-dialog title="选择易损设备" :visible.sync="vulnerableSelectorOpen" width="800px" append-to-body>
      <el-form :model="vulnerableQueryParams" ref="vulnerableQueryForm" size="small" :inline="true" label-width="68px">
        <el-form-item label="设备名称">
          <el-input
            v-model="vulnerableQueryParams.equipmentName"
            placeholder="请输入设备名称"
            clearable
            @keyup.enter.native="handleVulnerableQuery"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleVulnerableQuery">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetVulnerableQuery">重置</el-button>
        </el-form-item>
      </el-form>

      <el-table
        v-loading="vulnerableLoading"
        :data="vulnerableListSelector"
        @row-dblclick="handleSelectVulnerable"
        max-height="400"
      >
        <el-table-column label="易损编号" align="center" prop="vulnerableNumber"/>
        <el-table-column label="设备名称" align="center" prop="equipmentName"/>
        <el-table-column label="易损类型" align="center" prop="vulnerableType">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.vulnerable_type" :value="scope.row.vulnerableType"/>
          </template>
        </el-table-column>
        <el-table-column label="易损件" align="center" prop="vulnerablePart"/>
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-check"
              @click="handleSelectVulnerable(scope.row)"
            >选择
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <pagination
        v-show="vulnerableTotal>0"
        :total="vulnerableTotal"
        :page.sync="vulnerableQueryParams.pageNum"
        :limit.sync="vulnerableQueryParams.pageSize"
        @pagination="getVulnerableList"
      />

      <div slot="footer" class="dialog-footer">
        <el-button @click="vulnerableSelectorOpen = false">关 闭</el-button>
      </div>
    </el-dialog>

    <!-- 用户选择对话框 -->
    <el-dialog title="选择检查人" :visible.sync="userSelectorOpen" width="800px" append-to-body>
      <el-form :model="userQueryParams" ref="userQueryForm" size="small" :inline="true" label-width="68px">
        <el-form-item label="用户名称">
          <el-input
            v-model="userQueryParams.nickName"
            placeholder="请输入用户名称"
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
        <el-table-column label="用户名称" align="center" prop="nickName"/>
        <el-table-column label="部门" align="center" prop="dept.deptName"/>
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
  </div>
</template>

<script>
  import { getInspection, listInspection, addInspection, updateInspection, delInspection } from "@/api/vulnerable/inspection";
  import { listInfo } from "@/api/vulnerable/info";
  import { listUser } from "@/api/system/user";

  export default {
  name: "Inspection",
  dicts: ['vulnerable__status', 'vulnerable_type'],
  data() {
    return {
      //表格展示列
      columns: [
        { key: 0, label: '序号', visible: true },
          { key: 1, label: '检查编号', visible: true },
          { key: 2, label: '易损编号', visible: true },
          { key: 3, label: '设备名称', visible: true },
          { key: 4, label: '检查日期', visible: true },
          { key: 5, label: '检查人', visible: true },
          { key: 6, label: '检查照片', visible: true },
          { key: 7, label: '易损部位状态', visible: true },
          { key: 8, label: '问题描述', visible: true },
          { key: 9, label: '现场照片', visible: true },
          { key: 10, label: '处理措施', visible: true },
          { key: 11, label: '处理人', visible: true },
          { key: 12, label: '更换部件', visible: true },
          { key: 13, label: '更换后照片', visible: true },
          { key: 14, label: '备注', visible: true },
          { key: 15, label: '创建时间', visible: true },
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
      // 易损设备检查表格数据
      inspectionList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        equipmentName: null,
        vulnerablePartStatus: null,
      },
      // 表单参数
      form: {
        handlers: []
      },
      // 表单校验
      rules: {
        vulnerableNumbers: [
          { required: true, message: "易损编号不能为空", trigger: "blur" }
        ],
      },
      // 是否显示易损设备选择弹出层
      vulnerableSelectorOpen: false,
      // 易损设备列表
      vulnerableList: [],
      // 易损设备选择器列表
      vulnerableListSelector: [],
      // 易损设备总数
      vulnerableTotal: 0,
      // 易损设备加载
      vulnerableLoading: false,
      // 易损设备查询参数
      vulnerableQueryParams: {
        pageNum: 1,
        pageSize: 10,
        equipmentName: undefined
      },
      // 是否显示用户选择弹出层
      userSelectorOpen: false,
      // 用户列表
      userList: [],
      // 用户选择器列表
      userListSelector: [],
      // 用户总数
      userTotal: 0,
      // 用户加载
      userLoading: false,
      // 用户查询参数
      userQueryParams: {
        pageNum: 1,
        pageSize: 10,
        nickName: undefined
      },
    };
  },
  created() {
    this.getList();
    this.getVulnerableList();
    this.getUserList();
  },
  methods: {
    /** 查询易损设备检查列表 */
    getList() {
      this.loading = true;
      listInspection(this.queryParams).then(response => {
        this.inspectionList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 获取易损设备列表
    getVulnerableList() {
      this.vulnerableLoading = true;
      listInfo(this.vulnerableQueryParams).then(response => {
        this.vulnerableListSelector = response.rows;
        this.vulnerableTotal = response.total;
        this.vulnerableLoading = false;

        // 同时更新下拉框中的易损设备列表
        if (!this.vulnerableQueryParams.equipmentName) {
          this.vulnerableList = response.rows;
        }
      });
    },
    /** 易损设备搜索 */
    handleVulnerableQuery() {
      this.vulnerableQueryParams.pageNum = 1;
      this.getVulnerableList();
    },
    /** 重置易损设备搜索 */
    resetVulnerableQuery() {
      this.resetForm("vulnerableQueryForm");
      this.handleVulnerableQuery();
    },
    /** 选择易损设备 */
    handleSelectVulnerable(row) {
      // 如果还没有选择任何易损设备，则初始化数组
      if (!this.form.vulnerableNumbers) {
        this.$set(this.form, 'vulnerableNumbers', []);
      }

      // 检查是否已经选择了该易损设备
      const index = this.form.vulnerableNumbers.indexOf(row.vulnerableNumber);
      if (index === -1) {
        // 如果没有选择，则添加到数组中
        this.form.vulnerableNumbers.push(row.vulnerableNumber);
      } else {
        // 如果已经选择了，则从数组中移除
        this.form.vulnerableNumbers.splice(index, 1);
      }

      // 更新设备名称显示（多个设备名称用逗号分隔）
      this.updateEquipmentName();
    },
    /** 更新设备名称显示 */
    updateEquipmentName() {
      if (this.form.vulnerableNumbers && this.form.vulnerableNumbers.length > 0) {
        const selectedEquipments = this.vulnerableList
          .filter(item => this.form.vulnerableNumbers.includes(item.vulnerableNumber))
          .map(item => item.equipmentName);

        // 去重并用逗号连接
        this.form.equipmentName = [...new Set(selectedEquipments)].join(', ');
      } else {
        this.form.equipmentName = '';
      }
    },
    /** 打开易损设备选择器 */
    openVulnerableSelector() {
      this.vulnerableSelectorOpen = true;
      this.resetVulnerableQuery();
    },
    /** 易损设备变更 */
    handleVulnerableChange(val) {
      // 更新设备名称显示
      this.updateEquipmentName();
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        vulnerableNumbers: [],
        equipmentName: null,
        inspectionDate: null,
        inspectors: [],
        inspectionPhotos: null,
        vulnerablePartStatus: null,
        problemDescription: null,
        sitePhotos: null,
        handlingMeasures: null,
        handlers: [],
        replacedParts: null,
        afterReplacementPhotos: null,
        remarks: null,
        createdBy: null,
        createdTime: null,
        updatedBy: null,
        updatedTime: null
      };
      this.resetForm("form");
      this.selectingHandlers = false;
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加易损设备检查";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getInspection(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改易损设备检查";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          // 创建提交数据的副本
          const submitForm = Object.assign({}, this.form);

          // 将易损编号数组转换为逗号分隔的字符串
          if (submitForm.vulnerableNumbers && Array.isArray(submitForm.vulnerableNumbers)) {
            submitForm.vulnerableNumber = submitForm.vulnerableNumbers.join(',');
            delete submitForm.vulnerableNumbers;
          }

          // 将检查人数组转换为逗号分隔的字符串
          if (submitForm.inspectors && Array.isArray(submitForm.inspectors)) {
            submitForm.inspector = submitForm.inspectors.join(',');
            delete submitForm.inspectors;
          }

          // 将处理人数组转换为逗号分隔的字符串
          if (submitForm.handlers && Array.isArray(submitForm.handlers)) {
            submitForm.handler = submitForm.handlers.join(',');
            delete submitForm.handlers;
          }

          if (this.form.id != null) {
            updateInspection(submitForm).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addInspection(submitForm).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除易损设备检查编号为"' + ids + '"的数据项？').then(function() {
        return delInspection(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('vulnerable/inspection/export', {
        ...this.queryParams
      }, `inspection_${new Date().getTime()}.xlsx`)
    },
    // 获取用户列表
    getUserList() {
      this.userLoading = true;
      listUser(this.userQueryParams).then(response => {
        this.userListSelector = response.rows;
        this.userTotal = response.total;
        this.userLoading = false;

        // 同时更新下拉框中的用户列表
        if (!this.userQueryParams.nickName) {
          this.userList = response.rows;
        }
      });
    },
    /** 用户搜索 */
    handleUserQuery() {
      this.userQueryParams.pageNum = 1;
      this.getUserList();
    },
    /** 重置用户搜索 */
    resetUserQuery() {
      this.resetForm("userQueryForm");
      this.handleUserQuery();
    },
    /** 打开用户选择器 */
    openUserSelector() {
      this.userSelectorOpen = true;
      this.resetUserQuery();
      this.selectingHandlers = false;
    },
    /** 打开用户选择器用于选择处理人 */
    openUserSelectorForHandler() {
      this.userSelectorOpen = true;
      this.resetUserQuery();
      this.selectingHandlers = true;
    },
    // 查询用户（支持远程搜索）
    queryUsers(query) {
      if (query !== undefined && query !== '') {
        this.userQueryParams.nickName = query;
        this.getUserList();
      }
    },
    /** 选择用户 */
    handleSelectUser(row) {
      // 处理人选择逻辑
      if (this.selectingHandlers) {
        // 如果还没有选择任何处理人，则初始化数组
        if (!this.form.handlers) {
          this.$set(this.form, 'handlers', []);
        }

        // 检查是否已经选择了该用户
        const index = this.form.handlers.indexOf(row.userId);
        if (index === -1) {
          // 如果没有选择，则添加到数组中
          this.form.handlers.push(row.userId);
        } else {
          // 如果已经选择了，则从数组中移除
          this.form.handlers.splice(index, 1);
        }
      } else {
        // 检查人选择逻辑（原逻辑）
        // 如果还没有选择任何用户，则初始化数组
        if (!this.form.inspectors) {
          this.$set(this.form, 'inspectors', []);
        }

        // 检查是否已经选择了该用户
        const index = this.form.inspectors.indexOf(row.userId);
        if (index === -1) {
          // 如果没有选择，则添加到数组中
          this.form.inspectors.push(row.userId);
        } else {
          // 如果已经选择了，则从数组中移除
          this.form.inspectors.splice(index, 1);
        }
      }
    },
  }
};
</script>
