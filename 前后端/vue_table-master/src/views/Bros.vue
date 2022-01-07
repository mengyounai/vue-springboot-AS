<template>
  <div>
    子组件
      <button @click="sonClick">点我改变文本的值</button>
      <p>{{ words }}</p>
  </div>
</template>

<script>
export default {
  data() {
    return {
      words: ''
    }
  },
  created() {
    setInterval(() => {
      this.$eventBus.$on('backToSon', data => {
        this.words = data
        console.log(data)
      })
    }, 1000)
  },
  methods: {
    sonClick() {
      this.$eventBus.$emit('sendMsg', '我是孙子的值')
    }
  }
}
</script>

<style lang="less" scoped>

</style>
