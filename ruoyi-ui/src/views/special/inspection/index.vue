<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="设备种类" prop="equipmentType">
        <el-select v-model="queryParams.equipmentType" placeholder="请选择设备种类" clearable>
          <el-option
            v-for="dict in dict.type.special_equipment_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="检验类型" prop="inspectionType">
        <el-select v-model="queryParams.inspectionType" placeholder="请选择检验类型" clearable>
          <el-option
            v-for="dict in dict.type.equip_inspection_type"
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
          v-hasPermi="['special:inspection:add']"
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
          v-hasPermi="['special:inspection:edit']"
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
          v-hasPermi="['special:inspection:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['special:inspection:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="inspectionList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" v-if="columns[0].visible" prop="id" />
      <el-table-column label="设备种类" align="center" v-if="columns[1].visible" prop="equipmentType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.special_equipment_type" :value="scope.row.equipmentType"/>
        </template>
      </el-table-column>
      <el-table-column label="设备名称" :show-overflow-tooltip="true" align="center" v-if="columns[2].visible" prop="equipmentName" />
      <el-table-column label="检验类型" align="center" v-if="columns[3].visible" prop="inspectionType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.equip_inspection_type" :value="scope.row.inspectionType"/>
        </template>
      </el-table-column>
      <el-table-column label="使用部门" :show-overflow-tooltip="true" align="center" v-if="columns[4].visible" prop="userUnit" />
      <el-table-column label="管理人员" :show-overflow-tooltip="true" align="center" v-if="columns[5].visible" prop="manager" />
      <el-table-column label="联系电话" :show-overflow-tooltip="true" align="center" v-if="columns[6].visible" prop="contactPhone" />
      <el-table-column label="检定日期" align="center" v-if="columns[7].visible" prop="verificationDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.verificationDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="检验周期(月)" align="center" v-if="columns[8].visible" prop="inspectionCycle">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.equip_inspection_cycle" :value="scope.row.inspectionCycle"/>
        </template>
      </el-table-column>
      <el-table-column label="下次检验日期" align="center" v-if="columns[9].visible" prop="nextInspectionDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.nextInspectionDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="检验结论" align="center" v-if="columns[10].visible" prop="inspectionConclusion">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.inspection_conclusion" :value="scope.row.inspectionConclusion"/>
        </template>
      </el-table-column>
      <el-table-column label="检验机构" :show-overflow-tooltip="true" align="center" v-if="columns[11].visible" prop="inspectionAgency" />
      <el-table-column label="报告编号" :show-overflow-tooltip="true" align="center" v-if="columns[12].visible" prop="reportNo" />
      <el-table-column label="报告文本" align="center" v-if="columns[13].visible" prop="reportText" width="100">
        <template slot-scope="scope">
          <div v-if="scope.row.reportText">
            <el-tooltip placement="top">
              <div slot="content">
                <div v-for="(file,index) in scope.row.reportText.split(',')"
                     :key="index"
                     style="text-align: left;padding: 5px;">
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
            v-hasPermi="['special:inspection:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['special:inspection:remove']"
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

    <!-- 添加或修改检验检测对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="设备种类" prop="equipmentType">
          <el-select v-model="form.equipmentType" placeholder="请选择设备种类" @change="handleEquipmentTypeChange">
            <el-option
              v-for="dict in dict.type.special_equipment_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="设备名称" prop="equipmentName">
          <el-select
            v-model="form.equipmentName"
            placeholder="请选择设备名称"
            :loading="equipmentNameLoading"
            filterable
            remote
            :remote-method="queryEquipmentNames"
            v-if="showEquipmentSelect"
          >
            <el-option
              v-for="item in equipmentNameOptions"
              :key="item.value"
              :label="item.label"
              :value="item.label"
            ></el-option>
          </el-select>
          <el-input
            v-model="form.equipmentName"
            placeholder="请输入设备名称"
            v-else
          />
        </el-form-item>
        <el-form-item label="检验类型" prop="inspectionType">
          <el-select v-model="form.inspectionType" placeholder="请选择检验类型">
            <el-option
              v-for="dict in dict.type.equip_inspection_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="使用部门" prop="userUnit">
          <el-select
            v-model="form.userUnit"
            placeholder="请选择使用部门"
            filterable
            clearable
          >
            <el-option
              v-for="dept in deptOptions"
              :key="dept.deptId"
              :label="dept.deptName"
              :value="dept.deptName"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="管理人员" prop="manager">
          <el-select
            v-model="form.manager"
            placeholder="请选择管理人员"
            filterable
            remote
            :remote-method="queryUser"
            :loading="userLoading"
            clearable
          >
            <el-option
              v-for="user in userOptions"
              :key="user.userId"
              :label="user.nickName"
              :value="user.nickName"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="联系电话" prop="contactPhone">
          <el-input v-model="form.contactPhone" placeholder="请输入联系电话" />
        </el-form-item>
        <el-form-item label="检定日期" prop="verificationDate">
          <el-date-picker clearable
                          v-model="form.verificationDate"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择检定日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="检验周期(月)" prop="inspectionCycle">
          <el-select v-model="form.inspectionCycle" placeholder="请选择检验周期(月)">
            <el-option
              v-for="dict in dict.type.equip_inspection_cycle"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="下次检验日期" prop="nextInspectionDate">
          <el-date-picker clearable
                          v-model="form.nextInspectionDate"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择下次检验日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="检验结论" prop="inspectionConclusion">
          <el-select v-model="form.inspectionConclusion" placeholder="请选择检验结论">
            <el-option
              v-for="dict in dict.type.inspection_conclusion"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="检验机构" prop="inspectionAgency">
          <el-input v-model="form.inspectionAgency" placeholder="请输入检验机构" />
        </el-form-item>
        <el-form-item label="报告编号" prop="reportNo">
          <el-input v-model="form.reportNo" placeholder="请输入报告编号" />
        </el-form-item>
        <el-form-item label="报告文本" prop="reportText">
          <file-upload v-model="form.reportText"/>
        </el-form-item>
        <el-form-item label="备注" prop="remarks">
          <el-input v-model="form.remarks" placeholder="请输入备注" />
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
import { getInspection, listInspection, addInspection, updateInspection, delInspection } from "@/api/special/inspection";
import { listDept } from "@/api/system/dept";
import { listUser } from "@/api/system/user";
// 引入其他API
import { listData as getDictData } from "@/api/system/dict/data";
import { listPipelineData } from "@/api/special/data"; // 压力管道API
import { listInventory } from "@/api/special/inventory"; // 场(厂)内专用机动车辆API
import { listLedger } from "@/api/equip/ledger"; // 设备台账API

export default {
  name: "Inspection",
  dicts: ['special_equipment_type', 'inspection_conclusion', 'equip_inspection_type', 'equip_inspection_cycle'],
  data() {
    return {
      baseUrl: process.env.VUE_APP_BASE_API,
      //表格展示列
      columns: [
        { key: 0, label: '序号', visible: true },
        { key: 1, label: '设备种类', visible: true },
        { key: 2, label: '设备名称', visible: true },
        { key: 3, label: '检验类型', visible: true },
        { key: 4, label: '使用部门', visible: true },
        { key: 5, label: '管理人员', visible: true },
        { key: 6, label: '联系电话', visible: true },
        { key: 7, label: '检定日期', visible: true },
        { key: 8, label: '检验周期(月)', visible: true },
        { key: 9, label: '下次检验日期', visible: true },
        { key: 10, label: '检验结论', visible: true },
        { key: 11, label: '检验机构', visible: true },
        { key: 12, label: '报告编号', visible: true },
        { key: 13, label: '报告文本', visible: true },
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
      // 检验检测表格数据
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
        equipmentType: null,
        inspectionType: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
      // 部门选项
      deptOptions: [],
      // 用户选项
      userOptions: [],
      // 用户加载状态
      userLoading: false,
      // 设备名称选项
      equipmentNameOptions: [],
      // 设备名称加载状态
      equipmentNameLoading: false,
      // 是否显示设备选择下拉框
      showEquipmentSelect: false
    };
  },
  created() {
    this.getList();
    this.getDeptList();
    this.getUserList();
  },
  watch: {
    // 监听检定日期和检验周期的变化，自动计算下次检验日期
    'form.verificationDate': function(newVal) {
      this.calculateNextInspectionDate();
    },
    'form.inspectionCycle': function(newVal) {
      this.calculateNextInspectionDate();
    }
  },
  methods: {
    /** 查询检验检测列表 */
    getList() {
      this.loading = true;
      listInspection(this.queryParams).then(response => {
        this.inspectionList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 获取部门列表
    getDeptList() {
      listDept().then(response => {
        this.deptOptions = response.data;
      });
    },
    // 获取用户列表
    getUserList() {
      listUser({ pageSize: 1000 }).then(response => {
        this.userOptions = response.rows;
      });
    },
    // 查询用户
    queryUser(query) {
      if (query !== '') {
        this.userLoading = true;
        listUser({ nickName: query }).then(response => {
          this.userOptions = response.rows;
          this.userLoading = false;
        }).catch(() => {
          this.userLoading = false;
        });
      } else {
        this.userOptions = [];
      }
    },

    //获取文件名 此功能只可以下载只有一个文件的
    getFileName(filePath) {
      if (filePath == null) {
        return
      }
      // 提取文件名或根据需求生成文件名
      return filePath.substring(filePath.lastIndexOf('/') + 1)
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
        equipmentName: null,
        equipmentType: null,
        inspectionType: null,
        userUnit: null,
        manager: null,
        contactPhone: null,
        verificationDate: null,
        inspectionCycle: null,
        nextInspectionDate: null,
        inspectionConclusion: null,
        inspectionAgency: null,
        reportNo: null,
        reportText: null,
        remarks: null,
        createdBy: null,
        createdTime: null,
        updatedBy: null,
        updatedTime: null
      };
      this.resetForm("form");
      this.showEquipmentSelect = false;
      this.equipmentNameOptions = [];
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
      this.title = "添加检验检测";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getInspection(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改检验检测";
      });
    },
    // 计算下次检验日期
    calculateNextInspectionDate() {
      if (this.form.verificationDate && this.form.inspectionCycle) {
        const verificationDate = new Date(this.form.verificationDate);
        const months = parseInt(this.form.inspectionCycle);
        if (!isNaN(verificationDate.getTime()) && !isNaN(months)) {
          // 创建一个新的日期对象，避免修改原日期
          const nextDate = new Date(verificationDate);
          // 添加月数
          nextDate.setMonth(nextDate.getMonth() + months);
          // 格式化为 yyyy-MM-dd
          this.form.nextInspectionDate = this.formatDate(nextDate);
        }
      }
    },
    // 格式化日期为 yyyy-MM-dd
    formatDate(date) {
      const year = date.getFullYear();
      const month = String(date.getMonth() + 1).padStart(2, '0');
      const day = String(date.getDate()).padStart(2, '0');
      return `${year}-${month}-${day}`;
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateInspection(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addInspection(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除检验检测编号为"' + ids + '"的数据项？').then(function() {
        return delInspection(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('special/inspection/export', {
        ...this.queryParams
      }, `inspection_${new Date().getTime()}.xlsx`)
    },
    /** 设备种类变更 */
    handleEquipmentTypeChange(value) {
      // 清空设备名称
      this.form.equipmentName = "";
      this.equipmentNameOptions = [];

      // 根据设备种类显示不同的设备名称选择方式
      // 这里需要根据实际字典值来判断，暂时使用通用逻辑
      this.showEquipmentSelect = true;

      // 如果需要特定的字典值判断，可以使用以下方式:
      // if (value === "pressure_pipeline") { // 假设压力管道的值为pressure_pipeline
      //   this.loadPressurePipelines();
      // } else if (value === "vehicle") { // 假设场(厂)内专用机动车辆的值为vehicle
      //   this.loadVehicles();
      // } else {
      //   this.loadSpecialEquipment();
      // }
    },
    /** 查询设备名称 */
    queryEquipmentNames(query) {
      if (query !== '') {
        this.equipmentNameLoading = true;

        // 根据设备种类调用不同的API
        if (this.form.equipmentType) {
          // 压力管道
          if (this.form.equipmentType === "1") { // 假设压力管道的字典值为1
            listPipelineData({ pipelineName: query }).then(response => {
              this.equipmentNameOptions = response.rows.map(item => {
                return { label: item.pipelineName, value: item.pipelineId };
              });
              this.equipmentNameLoading = false;
            }).catch(() => {
              this.equipmentNameLoading = false;
            });
          }
          // 场(厂)内专用机动车辆
          else if (this.form.equipmentType === "2") { // 假设场(厂)内专用机动车辆的字典值为2
            listInventory({ deviceName: query }).then(response => {
              this.equipmentNameOptions = response.rows.map(item => {
                return { label: item.deviceName, value: item.id };
              });
              this.equipmentNameLoading = false;
            }).catch(() => {
              this.equipmentNameLoading = false;
            });
          }
          // 其他特种设备（从设备台账中获取）
          else {
            listLedger({
              equName: query,
              specialEquipment: "Y" // 只查询特种设备（是）
            }).then(response => {
              this.equipmentNameOptions = response.rows.map(item => {
                return { label: item.equName, value: item.equipId };
              });
              this.equipmentNameLoading = false;
            }).catch(() => {
              this.equipmentNameLoading = false;
            });
          }
        } else {
          this.equipmentNameLoading = false;
        }
      } else {
        this.equipmentNameOptions = [];
      }
    },
    /** 加载压力管道数据 */
    loadPressurePipelines() {
      this.equipmentNameLoading = true;
      listPipelineData().then(response => {
        this.equipmentNameOptions = response.rows.map(item => {
          return { label: item.pipelineName, value: item.pipelineId };
        });
        this.equipmentNameLoading = false;
      }).catch(() => {
        this.equipmentNameLoading = false;
      });
    },
    /** 加载场(厂)内专用机动车辆数据 */
    loadVehicles() {
      this.equipmentNameLoading = true;
      listInventory().then(response => {
        this.equipmentNameOptions = response.rows.map(item => {
          return { label: item.deviceName, value: item.id };
        });
        this.equipmentNameLoading = false;
      }).catch(() => {
        this.equipmentNameLoading = false;
      });
    },
    /** 加载其他特种设备数据 */
    loadSpecialEquipment() {
      this.equipmentNameLoading = true;
      listLedger({ specialEquipment: "Y" }).then(response => {
        this.equipmentNameOptions = response.rows.map(item => {
          return { label: item.equName, value: item.equipId };
        });
        this.equipmentNameLoading = false;
      }).catch(() => {
        this.equipmentNameLoading = false;
      });
    }
  }
};
</script>
