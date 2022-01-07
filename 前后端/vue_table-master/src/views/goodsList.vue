<template>
  <div class="box">
    <h1>商品列表</h1>
    <Header>
      <template v-slot:header="scope">{{scope.tip}}</template>
    </Header>
    <!-- <Son :obj= 'list' @tobab='getSon'/> -->
    <router-link to="/goods/listSon">点我去子页</router-link>
    <router-link to="/goods/goodsMy">点我去分类{{ lsData.name }}</router-link>
    <!-- <button>点我获取子类容</button> -->
    <button @click="flyTitle">发送兄弟组件标题</button>
    <hr>
    <br>
    <input type="text" v-model="text">
     <Brother>
        <Bros />
    </Brother>
  </div>
</template>

<script>
// import Son from './Son'
import Header from '../components/List'
import Brother from './Brother'
import Bros from './Bros'

export default {
  components: {
    // Son,
    Header,
    Brother,
    Bros
  },
  created() {
    this.$eventBus.$on('getData', (data) => {
      console.log('我收到了子传来的数据')
      this.lsData = data
    })
    // 传到子组件，然后子组件传到兄弟组件
    this.$eventBus.$on('xxx', (data) => {
      this.$eventBus.$emit('xxxs', '')
    })
    this.$eventBus.$on('sendMsg', (data) => {
      this.text = data
      this.$eventBus.$emit('backToSon', '我已经接收到了你的消息')
    })
  },
  data() {
    return {
      list: { id: 1, name: '张三' },
      lsData: {},
      obj2: {},
      title: '饿了么',
      text: '哈哈哈'
    }
  },
  methods: {
    getSon(e) {
      // console.log(e)
      this.obj2 = e
      console.log(this.obj2)
    },
    flyTitle() {
      // 传到兄弟组件
      this.$eventBus.$emit('pushTitle', this.title)
    }
  }
}
</script>

<style lang="less" scoped>
.box{
  width: 360px;
  height: 400px;
  background-color: rgb(227, 184, 255);
}
h1{
  background-color: rgb(165, 165, 255);
  color: #fff;
  font-size: 80px;
}
</style>
