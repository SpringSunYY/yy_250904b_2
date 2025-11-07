import { listSupplier } from '@/api/pur/supplier'
import { listDept } from '@/api/system/dept'
import { listUser } from '@/api/system/user'
import { getDicts } from '@/api/system/dict/data'
import { listLedger } from '@/api/equip/ledger'
import { listInspe } from '@/api/inspe/inspe'
import { listPlan } from '@/api/lubr/plan'
import { listRepairPlan } from '@/api/repair/plan'
import { listOrder } from '@/api/pur/order'

const GlobalApi = {
  //供应商
  async listSupplierForm(query) {
    const res = await listSupplier(query)
    const options = res.rows.map(item => { // 直接转换为 options 数组
      return {
        label: item.supplierName,
        value: item.supplierId
      }
    })
    return options || [] // 直接返回 options 数组
  },
  //申请部门
  async listDeptForm(query) {
    const res = await listDept(query)
    const options = res.data.map(item => { // 直接转换为 options 数组
      return {
        label: item.deptName,
        value: item.deptId
      }
    })
    return options || [] // 直接返回 options 数组
  },
  //用户
  listUserForm: async function(query) {
    const res = await listUser(query)
    const options = res.rows.map(item => {
      return {
        label: item.userName,
        value: item.userId
      }
    })
    return options || []
  },
  //字典
  listDictForm: async function(query) {
    const res = await getDicts(query)
    return res.data.map(item => {
      return {
        label: item.dictLabel,
        value: item.dictValue.toString()
      }
    })
  },
  //设备
  listLedgerForm: async function(query) {
    const res = await listLedger(query)
    return res.rows.map(item => {
      return {
        label: item.equName + ':' + item.equCode,
        value: item.equipId,
        data: item
      }
    })
  },
  //检查记录列表
  listInspeForm: async function(query) {
    const res = await listInspe(query)
    return res.rows.map(item => {
      return {
        label: item.inspeNo,
        value: item.inspeId
      }
    })
  },
  //订单列表
  listOrderForm: async function(query) {
    const res = await listOrder(query)
    return res.rows.map(item => {
      return {
        label: item.orderNo + ':' + item.equipName,
        value: item.orderId
      }
    })
  },

  //计划列表
  listLubrPlanForm: async function(query) {
    const res = await listPlan(query)
    return res.rows.map(item => {
      return {
        label: item.planNo,
        value: item.planId
      }
    })
  },
  //维修计划
  listRepairPlanForm: async function(query) {
    const res = await listRepairPlan(query)
    return res.rows.map(item => {
      return {
        label: item.planNo,
        value: item.planId
      }
    })
  }
}

// 导出一个 Vue 插件对象
export default {
  install(Vue) {
    // 关键步骤：将整个 API 对象挂载到 Vue 原型上
    // $globalApi 这个命名非常清晰，不会造成污染
    Vue.prototype.$globalApi = GlobalApi
  }
}
