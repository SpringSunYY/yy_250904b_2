import request from '@/utils/request'

// 查询承包商选择详细
export function getChoice(choiceId) {
  return request({
    url: '/choice/choice/' + choiceId,
    method: 'get'
  })
}

export function listChoice(query) {
  return request({
    url: '/choice/choice/list',
    method: 'get',
    params: query
  })
}

// 新增承包商选择
export function addChoice(data) {
  return request({
    url: '/choice/choice',
    method: 'post',
    data: data
  })
}

// 修改承包商选择
export function updateChoice(data) {
  return request({
    url: '/choice/choice',
    method: 'put',
    data: data
  })
}

// 删除承包商选择
export function delChoice(choiceId) {
  return request({
    url: '/choice/choice/' + choiceId,
    method: 'delete'
  })
}

export function handleSelectProjectRow(row) {
  this.form.projectName = row.projectName;
  this.form.contractorName = row.selectedContractor;
  this.form.choiceId = row.choiceId;
  // 加载该承包商的人员信息
  this.getContractorPersonnelList(this.form.contractorName);
  this.projectSelectOpen = false;
}

// 表单重置
export function reset(formRef, form, selectedInspector, contractorPersonnelList) {
  form = {
    id: null,
    inspectionCode: null,
    choiceId: null,
    projectName: null,
    contractorName: null,
    inspectionDatetime: null,
    weatherCondition: null,
    workLocation: null,
    inspectorName: null,
    contractorAttendant: null,
    hasHazards: null,
    hazardsCount: null,
    attachments: null,
    notes: null,
    createdBy: null,
    createdAt: null,
    updatedBy: null,
    updatedAt: null
  };
  selectedInspector = [];
  contractorPersonnelList = [];
  if (formRef) {
    formRef.resetFields();
  }
}
