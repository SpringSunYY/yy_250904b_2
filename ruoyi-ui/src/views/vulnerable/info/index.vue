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
      <el-form-item label="易损类型" prop="vulnerableType">
        <el-input
          v-model="queryParams.vulnerableType"
          placeholder="请输入易损类型"
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
          v-hasPermi="['vulnerable:info:add']"
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
          v-hasPermi="['vulnerable:info:edit']"
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
          v-hasPermi="['vulnerable:info:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['vulnerable:info:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="infoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" v-if="columns[0].visible" prop="id" />
      <el-table-column label="易损编号" :show-overflow-tooltip="true" align="center" v-if="columns[1].visible" prop="vulnerableNumber" />
      <el-table-column label="设备名称" :show-overflow-tooltip="true" align="center" v-if="columns[2].visible" prop="equipmentName" />
      <el-table-column label="易损类型" :show-overflow-tooltip="true" align="center" v-if="columns[3].visible" prop="vulnerableType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.vulnerable_type" :value="scope.row.vulnerableType"/>
        </template>
      </el-table-column>
      <el-table-column label="易损件" :show-overflow-tooltip="true" align="center" v-if="columns[4].visible" prop="vulnerablePart" />
      <el-table-column label="安装位置" :show-overflow-tooltip="true" align="center" v-if="columns[5].visible" prop="installationLocation" />
      <el-table-column label="建议更换周期(月)" align="center" v-if="columns[6].visible" prop="replacementCycleMonths">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.equip_lub_interval" :value="scope.row.replacementCycleMonths"/>
        </template>
      </el-table-column>
      <el-table-column label="当前状态" align="center" v-if="columns[7].visible" prop="currentStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.vulnerable__status" :value="scope.row.currentStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="备注" :show-overflow-tooltip="true" align="center" v-if="columns[8].visible" prop="remarks" />
      <el-table-column label="创建时间" align="center" v-if="columns[9].visible" prop="createdTime" width="180">
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
            v-hasPermi="['vulnerable:info:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['vulnerable:info:remove']"
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

    <!-- 添加或修改易损设备信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="900px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="设备名称" prop="equipmentId">
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
        <el-form-item label="易损类型" prop="vulnerableType">
          <el-select v-model="form.vulnerableType" placeholder="请选择易损类型">
            <el-option
              v-for="dict in dict.type.vulnerable_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="易损件" prop="vulnerablePart">
          <el-input v-model="form.vulnerablePart" placeholder="请输入易损件" />
        </el-form-item>
        <el-form-item label="安装位置" prop="installationLocation">
          <el-input v-model="form.installationLocation" placeholder="请输入安装位置" />
        </el-form-item>
        <el-form-item label="建议更换周期(月)" prop="replacementCycleMonths">
          <el-select v-model="form.replacementCycleMonths" placeholder="请选择建议更换周期">
            <el-option
              v-for="dict in dict.type.equip_lub_interval"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="当前状态" prop="currentStatus">
          <el-select v-model="form.currentStatus" placeholder="请选择当前状态">
            <el-option
              v-for="dict in dict.type.vulnerable__status"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
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
import { getInfo, listInfo, addInfo, updateInfo, delInfo } from "@/api/vulnerable/info";
import { listLedger } from "@/api/equip/ledger";

export default {
  name: "Info",
  dicts: ['vulnerable__status', 'vulnerable_type', 'equip_lub_interval', 'equip_type', 'equip_levels', 'equip_location'],
  data() {
    return {
      //表格展示列
      columns: [
        { key: 0, label: '序号', visible: true },
          { key: 1, label: '易损编号', visible: true },
          { key: 2, label: '设备名称', visible: true },
          { key: 3, label: '易损类型', visible: true },
          { key: 4, label: '易损件', visible: true },
          { key: 5, label: '安装位置', visible: true },
          { key: 6, label: '建议更换周期(月)', visible: true },
          { key: 7, label: '当前状态', visible: true },
          { key: 8, label: '备注', visible: true },
          { key: 9, label: '创建时间', visible: true },
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
      // 易损设备信息表格数据
      infoList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 是否显示设备选择弹出层
      equipSelectorOpen: false,
      // 设备列表
      equipList: [],
      // 设备选择器列表
      equipListSelector: [],
      // 设备总数
      equipTotal: 0,
      // 设备加载
      equipLoading: false,
      // 设备查询参数
      equipQueryParams: {
        pageNum: 1,
        pageSize: 10,
        equName: undefined,
        equCode: undefined
      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        equipmentName: null,
        vulnerableType: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        equipmentId: [
          { required: true, message: "设备不能为空", trigger: "change" }
        ],
        equipmentName: [
          { required: true, message: "设备名称不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
    this.getEquipList();
  },
  methods: {
    /** 查询易损设备信息列表 */
    getList() {
      this.loading = true;
      listInfo(this.queryParams).then(response => {
        this.infoList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 获取设备列表
    getEquipList() {
      this.equipLoading = true;
      listLedger(this.equipQueryParams).then(response => {
        this.equipListSelector = response.rows;
        this.equipTotal = response.total;
        this.equipLoading = false;

        // 同时更新下拉框中的设备列表
        if (!this.equipQueryParams.equName && !this.equipQueryParams.equCode) {
          this.equipList = response.rows;
        }
      });
    },
    /** 设备搜索 */
    handleEquipQuery() {
      this.equipQueryParams.pageNum = 1;
      this.getEquipList();
    },
    /** 重置设备搜索 */
    resetEquipQuery() {
      this.resetForm("equipQueryForm");
      this.handleEquipQuery();
    },
    /** 选择设备 */
    handleSelectEquip(row) {
      this.form.equipmentId = row.equipId;
      this.form.equipmentName = row.equName;
      this.equipSelectorOpen = false;
    },
    /** 打开设备选择器 */
    openEquipSelector() {
      this.equipSelectorOpen = true;
      this.resetEquipQuery();
    },
    /** 设备变更 */
    handleEquipChange(val) {
      const selectedEquip = this.equipList.find(item => item.equipId === val);
      if (selectedEquip) {
        this.form.equipmentName = selectedEquip.equName;
      }
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        equipmentId: null,
        equipmentName: null,
        vulnerableType: null,
        vulnerablePart: null,
        installationLocation: null,
        replacementCycleMonths: null,
        currentStatus: null,
        remarks: null,
        createdBy: null,
        createdTime: null,
        updatedBy: null,
        updatedTime: null
      };
      this.resetForm("form");
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
      this.title = "添加易损设备信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getInfo(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改易损设备信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateInfo(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addInfo(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除易损设备信息编号为"' + ids + '"的数据项？').then(function() {
        return delInfo(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
        // 添加适当的错误处理，避免静默失败
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('vulnerable/info/export', {
        ...this.queryParams
      }, `info_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
