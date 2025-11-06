<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="管道名称" prop="pipelineName">
        <el-input
          v-model="queryParams.pipelineName"
          placeholder="请输入管道名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否压力管道" prop="pressurePipeline">
        <el-select v-model="queryParams.pressurePipeline" placeholder="请选择是否压力管道" clearable>
          <el-option
            v-for="dict in dict.type.sys_yes_no"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="管道等级" prop="pipelineClass">
        <el-select v-model="queryParams.pipelineClass" placeholder="请选择管道等级" clearable>
          <el-option
            v-for="dict in dict.type.pipeline_class"
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
          v-hasPermi="['special:data:add']"
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
          v-hasPermi="['special:data:edit']"
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
          v-hasPermi="['special:data:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['special:data:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="dataList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" v-if="columns[0].visible">
        <template slot-scope="scope">
          <span>{{ (queryParams.pageNum - 1) * queryParams.pageSize + scope.$index + 1 }}</span>
        </template>
      </el-table-column>
        <el-table-column label="管道编号" :show-overflow-tooltip="true" align="center" v-if="columns[1].visible" prop="pipelineNo" />
        <el-table-column label="管道名称" :show-overflow-tooltip="true" align="center" v-if="columns[2].visible" prop="pipelineName" />
        <el-table-column label="是否压力管道" align="center" v-if="columns[3].visible" prop="pressurePipeline">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_yes_no" :value="scope.row.pressurePipeline"/>
        </template>
      </el-table-column>
        <el-table-column label="管道等级" align="center" v-if="columns[4].visible" prop="pipelineClass">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.pipeline_class" :value="scope.row.pipelineClass"/>
        </template>
      </el-table-column>
        <el-table-column label="材质" align="center" v-if="columns[5].visible" prop="material">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.special_material" :value="scope.row.material"/>
        </template>
      </el-table-column>
        <el-table-column label="设计单位" :show-overflow-tooltip="true" align="center" v-if="columns[6].visible" prop="designUnit" />
        <el-table-column label="安装单位" :show-overflow-tooltip="true" align="center" v-if="columns[7].visible" prop="installationUnit" />
        <el-table-column label="安装日期" align="center" v-if="columns[8].visible" prop="installationDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.installationDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
        <el-table-column label="投用日期" align="center" v-if="columns[9].visible" prop="commissioningDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.commissioningDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
        <el-table-column label="隔热代号" :show-overflow-tooltip="true" align="center" v-if="columns[10].visible" prop="insulationCode" />
        <el-table-column label="公称直径(mm)" :show-overflow-tooltip="true" align="center" v-if="columns[11].visible" prop="nominalDiameter" />
        <el-table-column label="公称壁厚(mm)" :show-overflow-tooltip="true" align="center" v-if="columns[12].visible" prop="nominalWallThickness" />
        <el-table-column label="管道壁厚等级" :show-overflow-tooltip="true" align="center" v-if="columns[13].visible" prop="wallThicknessClass" />
        <el-table-column label="介质名称" :show-overflow-tooltip="true" align="center" v-if="columns[14].visible" prop="mediumName" />
        <el-table-column label="介质状态" align="center" v-if="columns[15].visible" prop="mediumState">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.medium_state" :value="scope.row.mediumState"/>
        </template>
      </el-table-column>
        <el-table-column label="管道长度" :show-overflow-tooltip="true" align="center" v-if="columns[16].visible" prop="pipelineLength" />
        <el-table-column label="起点" :show-overflow-tooltip="true" align="center" v-if="columns[17].visible" prop="startPoint" />
        <el-table-column label="止点" :show-overflow-tooltip="true" align="center" v-if="columns[18].visible" prop="endPoint" />
        <el-table-column label="工作压力(MPa)" :show-overflow-tooltip="true" align="center" v-if="columns[19].visible" prop="operatingPressure" />
        <el-table-column label="工作温度(℃)" :show-overflow-tooltip="true" align="center" v-if="columns[20].visible" prop="operatingTemperature" />
        <el-table-column label="设计压力(MPa)" :show-overflow-tooltip="true" align="center" v-if="columns[21].visible" prop="designPressure" />
        <el-table-column label="设计温度(℃)" :show-overflow-tooltip="true" align="center" v-if="columns[22].visible" prop="designTemperature" />
        <el-table-column label="压力试验类型" align="center" v-if="columns[23].visible" prop="pressureTestType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.pressure_test_type" :value="scope.row.pressureTestType"/>
        </template>
      </el-table-column>
        <el-table-column label="试验压力" :show-overflow-tooltip="true" align="center" v-if="columns[24].visible" prop="testPressure" />
        <el-table-column label="安全等级" align="center" v-if="columns[25].visible" prop="safetyClass">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.pipeline_safety_class" :value="scope.row.safetyClass"/>
        </template>
      </el-table-column>
        <el-table-column label="使用登记证文本" align="center" v-if="columns[26].visible" prop="usageRegistrationCertificate" width="100">
        <template slot-scope="scope">
          <image-preview :src="scope.row.usageRegistrationCertificate" :width="50" :height="50"/>
        </template>
      </el-table-column>
        <el-table-column label="备注" :show-overflow-tooltip="true" align="center" v-if="columns[27].visible" prop="remarks" />
        <el-table-column label="创建时间" align="center" v-if="columns[28].visible" prop="createdTime" width="180">
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
            v-hasPermi="['special:data:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['special:data:remove']"
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

    <!-- 添加或修改管道数据对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="管道编号" prop="pipelineNo">
          <el-input v-model="form.pipelineNo" placeholder="请输入管道编号" />
        </el-form-item>
        <el-form-item label="管道名称" prop="pipelineName">
          <el-input v-model="form.pipelineName" placeholder="请输入管道名称" />
        </el-form-item>
        <el-form-item label="是否压力管道" prop="pressurePipeline">
          <el-select v-model="form.pressurePipeline" placeholder="请选择是否压力管道">
            <el-option
              v-for="dict in dict.type.sys_yes_no"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="管道等级" prop="pipelineClass">
          <el-select v-model="form.pipelineClass" placeholder="请选择管道等级">
            <el-option
              v-for="dict in dict.type.pipeline_class"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
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
        <el-form-item label="设计单位" prop="designUnit">
          <el-input v-model="form.designUnit" placeholder="请输入设计单位" />
        </el-form-item>
        <el-form-item label="安装单位" prop="installationUnit">
          <el-input v-model="form.installationUnit" placeholder="请输入安装单位" />
        </el-form-item>
        <el-form-item label="安装日期" prop="installationDate">
          <el-date-picker clearable
            v-model="form.installationDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择安装日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="投用日期" prop="commissioningDate">
          <el-date-picker clearable
            v-model="form.commissioningDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择投用日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="隔热代号" prop="insulationCode">
          <el-input v-model="form.insulationCode" placeholder="请输入隔热代号" />
        </el-form-item>
        <el-form-item label="公称直径(mm)" prop="nominalDiameter">
          <el-input v-model="form.nominalDiameter" placeholder="请输入公称直径(mm)" />
        </el-form-item>
        <el-form-item label="公称壁厚(mm)" prop="nominalWallThickness">
          <el-input v-model="form.nominalWallThickness" placeholder="请输入公称壁厚(mm)" />
        </el-form-item>
        <el-form-item label="管道壁厚等级" prop="wallThicknessClass">
          <el-input v-model="form.wallThicknessClass" placeholder="请输入管道壁厚等级" />
        </el-form-item>
        <el-form-item label="介质名称" prop="mediumName">
          <el-input v-model="form.mediumName" placeholder="请输入介质名称" />
        </el-form-item>
        <el-form-item label="介质状态" prop="mediumState">
          <el-select v-model="form.mediumState" placeholder="请选择介质状态">
            <el-option
              v-for="dict in dict.type.medium_state"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="管道长度" prop="pipelineLength">
          <el-input v-model="form.pipelineLength" placeholder="请输入管道长度" />
        </el-form-item>
        <el-form-item label="起点" prop="startPoint">
          <el-input v-model="form.startPoint" placeholder="请输入起点" />
        </el-form-item>
        <el-form-item label="止点" prop="endPoint">
          <el-input v-model="form.endPoint" placeholder="请输入止点" />
        </el-form-item>
        <el-form-item label="工作压力(MPa)" prop="operatingPressure">
          <el-input v-model="form.operatingPressure" placeholder="请输入工作压力(MPa)" />
        </el-form-item>
        <el-form-item label="工作温度(℃)" prop="operatingTemperature">
          <el-input v-model="form.operatingTemperature" placeholder="请输入工作温度(℃)" />
        </el-form-item>
        <el-form-item label="设计压力(MPa)" prop="designPressure">
          <el-input v-model="form.designPressure" placeholder="请输入设计压力(MPa)" />
        </el-form-item>
        <el-form-item label="设计温度(℃)" prop="designTemperature">
          <el-input v-model="form.designTemperature" placeholder="请输入设计温度(℃)" />
        </el-form-item>
        <el-form-item label="压力试验类型" prop="pressureTestType">
          <el-select v-model="form.pressureTestType" placeholder="请选择压力试验类型">
            <el-option
              v-for="dict in dict.type.pressure_test_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="试验压力" prop="testPressure">
          <el-input v-model="form.testPressure" placeholder="请输入试验压力" />
        </el-form-item>
        <el-form-item label="安全等级" prop="safetyClass">
          <el-select v-model="form.safetyClass" placeholder="请选择安全等级">
            <el-option
              v-for="dict in dict.type.pipeline_safety_class"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="使用登记证文本" prop="usageRegistrationCertificate">
          <image-upload v-model="form.usageRegistrationCertificate"/>
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
  import { getData, listData, addData, updateData, delData } from "@/api/special/data";

  export default {
  name: "Data",
  dicts: ['special_material', 'pipeline_class', 'sys_yes_no', 'medium_state', 'pressure_test_type', 'pipeline_safety_class'],
  data() {
    return {
      //表格展示列
      columns: [
        { key: 0, label: '序号', visible: true },
          { key: 1, label: '管道编号', visible: true },
          { key: 2, label: '管道名称', visible: true },
          { key: 3, label: '是否压力管道', visible: true },
          { key: 4, label: '管道等级', visible: true },
          { key: 5, label: '材质', visible: true },
          { key: 6, label: '设计单位', visible: true },
          { key: 7, label: '安装单位', visible: true },
          { key: 8, label: '安装日期', visible: true },
          { key: 9, label: '投用日期', visible: true },
          { key: 10, label: '隔热代号', visible: true },
          { key: 11, label: '公称直径(mm)', visible: true },
          { key: 12, label: '公称壁厚(mm)', visible: true },
          { key: 13, label: '管道壁厚等级', visible: true },
          { key: 14, label: '介质名称', visible: true },
          { key: 15, label: '介质状态', visible: true },
          { key: 16, label: '管道长度', visible: true },
          { key: 17, label: '起点', visible: true },
          { key: 18, label: '止点', visible: true },
          { key: 19, label: '工作压力(MPa)', visible: true },
          { key: 20, label: '工作温度(℃)', visible: true },
          { key: 21, label: '设计压力(MPa)', visible: true },
          { key: 22, label: '设计温度(℃)', visible: true },
          { key: 23, label: '压力试验类型', visible: true },
          { key: 24, label: '试验压力', visible: true },
          { key: 25, label: '安全等级', visible: true },
          { key: 26, label: '使用登记证文本', visible: true },
          { key: 27, label: '备注', visible: true },
          { key: 28, label: '创建时间', visible: true },
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
      // 管道数据表格数据
      dataList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        pipelineName: null,
        pressurePipeline: null,
        pipelineClass: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询管道数据列表 */
    getList() {
      this.loading = true;
      listData(this.queryParams).then(response => {
        this.dataList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        pipelineId: null,
        pipelineNo: null,
        pipelineName: null,
        pressurePipeline: null,
        pipelineClass: null,
        material: null,
        designUnit: null,
        installationUnit: null,
        installationDate: null,
        commissioningDate: null,
        insulationCode: null,
        nominalDiameter: null,
        nominalWallThickness: null,
        wallThicknessClass: null,
        mediumName: null,
        mediumState: null,
        pipelineLength: null,
        startPoint: null,
        endPoint: null,
        operatingPressure: null,
        operatingTemperature: null,
        designPressure: null,
        designTemperature: null,
        pressureTestType: null,
        testPressure: null,
        safetyClass: null,
        usageRegistrationCertificate: null,
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
      this.ids = selection.map(item => item.pipelineId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加管道数据";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const pipelineId = row.pipelineId || this.ids
      getData(pipelineId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改管道数据";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.pipelineId != null) {
            updateData(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addData(this.form).then(response => {
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
      const pipelineIds = row.pipelineId || this.ids;
      this.$modal.confirm('是否确认删除管道数据编号为"' + pipelineIds + '"的数据项？').then(function() {
        return delData(pipelineIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('special/data/export', {
        ...this.queryParams
      }, `data_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
