<template>
  <div class="rich-text-widget">
    <el-input
      type="textarea"
      :value="content"
      @input="handleInput"
      :rows="4"
      :placeholder="placeholder"
    ></el-input>
  </div>
</template>

<script>
export default {
  name: 'rich-text-widget',
  props: {
    value: {
      type: String,
      default: ''
    },
    placeholder: {
      type: String,
      default: '请输入内容'
    }
  },
  data() {
    return {
      content: this.value
    }
  },
  watch: {
    value(newVal) {
      this.content = newVal
    }
  },
  methods: {
    handleInput(val) {
      this.content = val
      this.$emit('input', val)
    }
  }
}
</script>

<style scoped>
.rich-text-widget {
  width: 100%;
}
</style>