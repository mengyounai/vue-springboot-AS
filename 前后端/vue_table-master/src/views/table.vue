<template>
  <div class="sales-table">
    <div class="order-list-header">订单列表</div>
    <div class="back-box">
      <div class="search-box">
        <input type="text" class="order-search-input" placeholder="关键词" v-model='search'>
      </div>
    </div>
  <div class="table-box">
    <div class="table-list" v-for="(cash, index) in orderList.slice((currentPage-1)*pagesize, currentPage*pagesize)" :key="cash.id">
     <table id="tableSort" style="table-layout:fixed;">
          <thead class="table-header">
            <tr>
              <th class="left-radius">序号</th>
              <th>创建时间</th>
              <th>订单ID</th>
              <th>所属用户姓名</th>
              <th>所属用户ID</th>
              <th>所属用户手机</th>
              <th>所属用户层级</th>
              <th>订单金额</th>
              <th>订单状态</th>
              <th>审核状态</th>
              <th>收件人</th>
              <th>联系电话</th>
              <th>收货地址</th>
              <th>订单备注</th>
              <th class="right-radius">操作</th>
            </tr>
          </thead>
          <tbody class="table-lists">
            <tr class="first-tr">
              <td class="sequence">{{ index+1>9?index+1:"0"+(index+1) }}</td>
              <td class="sequence">{{cash.createTime}}</td>
              <td class="sequence">{{cash.orderId}}</td>
              <td class="sequence">{{cash.cilentName}}</td>
              <td class="sequence">{{cash.cilentId}}</td>
              <td class="sequence">{{cash.cilentPhone}}</td>
              <td class="sequence">{{cash.cilentGrade}}</td>
              <td class="sequence money">￥{{cash.orderPrice}}</td>
              <td class="sequence">{{cash.orderState}}</td>
              <td class="sequence">{{cash.auditState}}</td>
              <td class="sequence">{{cash.receiver}}</td>
              <td class="sequence">{{cash.phone}}</td>
              <td class="sequence">{{cash.address}}</td>
              <td class="sequence">{{cash.orderRemark}}</td>
              <td class="sequence"><a class="view-order">查看</a><a class="edit-order">编辑</a><a class="delete-order">删除</a></td>
            </tr>
          </tbody>
      </table>
        </div>
    </div>
    <div class="page">
      <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="currentPage"
      :page-sizes="[5, 10, 20, 40]"
      :page-size="pagesize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="Cashdata.length">
      </el-pagination>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      currentPage: 1, // 初始页
      pagesize: 5, // 每页的数据// 搜索关键词
      search: '',
      Cashdata: [
        {
          createTime: '2019/1/21/ 14:30:30',
          orderId: '1555555454',
          cilentName: '峡谷之巅',
          cilentId: '21313216544',
          cilentPhone: '13976605432',
          cilentGrade: '1',
          orderPrice: '454664',
          orderState: '已提交',
          auditState: '系统已确认',
          receiver: '和大宝',
          phone: '16565644444',
          address: '广东省广州市*************************',
          orderRemark: '少放醋，多方唐撒旦啊阿萨大萨达'
        },
        {
          createTime: '2019/1/21/ 14:30:30',
          orderId: '1555555454',
          cilentName: '峡谷先锋',
          cilentId: '21313216544',
          cilentPhone: '13976605432',
          cilentGrade: '1',
          orderPrice: '454664',
          orderState: '已提交',
          auditState: '系统已确认',
          receiver: '和大宝',
          phone: '16565644444',
          address: '广东省惠州市*************************',
          orderRemark: '少放醋，多方唐撒旦啊阿萨大萨达'
        },
        {
          createTime: '2019/1/21/ 14:30:30',
          orderId: '1555555454',
          cilentName: '均衡教派',
          cilentId: '21313216544',
          cilentPhone: '13976605432',
          cilentGrade: '1',
          orderPrice: '454664',
          orderState: '已提交',
          auditState: '系统已确认',
          receiver: '和大宝',
          phone: '16565644444',
          address: '湖北省武汉市*************************',
          orderRemark: '少放醋，多方唐撒旦啊阿萨大萨达'
        },
        {
          createTime: '2019/1/21/ 14:30:30',
          orderId: '1555555454',
          cilentName: '黑色玫瑰',
          cilentId: '21313216544',
          cilentPhone: '13976605432',
          cilentGrade: '1',
          orderPrice: '454664',
          orderState: '已提交',
          auditState: '系统已确认',
          receiver: '和大宝',
          phone: '16565644444',
          address: '北京市*************************',
          orderRemark: '少放醋，多方唐撒旦啊阿萨大萨达'
        },
        {
          createTime: '2019/1/21/ 14:30:30',
          orderId: '1555555454',
          cilentName: '祖安',
          cilentId: '21313216544',
          cilentPhone: '13976605432',
          cilentGrade: '1',
          orderPrice: '454664',
          orderState: '已提交',
          auditState: '系统已确认',
          receiver: '和大宝',
          phone: '16565644444',
          address: '广州市深圳市*************************',
          orderRemark: '少放醋，多方唐撒旦啊阿萨大萨达'
        },
        {
          createTime: '2019/1/21/ 14:30:30',
          orderId: '1555555454',
          cilentName: '艾欧尼亚',
          cilentId: '21313216544',
          cilentPhone: '13976605432',
          cilentGrade: '1',
          orderPrice: '454664',
          orderState: '已提交',
          auditState: '系统已确认',
          receiver: '和大宝',
          phone: '16565644444',
          address: '广东省深圳市*************************',
          orderRemark: '少放醋，多方唐撒旦啊阿萨大萨达'
        }
      ]
    }
  },
  methods: {
    // 初始页currentPage、初始每页数据数pagesize和数据data
    handleSizeChange: function (size) {
      this.pagesize = size
      // console.log(this.pagesize) // 每页下拉显示数据
    },
    handleCurrentChange: function(currentPage) {
      this.currentPage = currentPage
      document.documentElement.scrollTop = 0 // 点击翻页的时候回到顶部
      // console.log(this.currentPage) // 点击第几页
    }
  },
  // 订单列表搜索关键字
  computed: {
    orderList: function() {
      var _search = this.search
      if (_search) {
        return this.Cashdata.filter(function(product) {
          return Object.keys(product).some(function(key) {
            return String(product[key]).toLowerCase().indexOf(_search) > -1
          })
        })
      }
      return this.Cashdata
    }
  }
}
</script>

<style lang="less" scoped>
/deep/ .el-pagination{
 margin-bottom: 30px;
 margin-top: 30px;
 float: right;
 font-size: 20px;
 color: #333333;
 margin-right: 55px;
 font-weight: normal;

 .el-select .el-input{
 width: 126px;
 height: 36px;
 }
 .el-select .el-input .el-input__inner{
 height: 100%;
 font-size: 20px;
 color: #333333;
 }
 .el-pagination__editor.el-input .el-input__inner{
 height: 36px;
 }
 .btn-prev,.btn-next{
 height: 36px;
 }
 .btn-prev{
 border-radius: 5px 0 0 5px;
 }
 .btn-next{
 border-radius: 0 5px 5px 0;
 }
 .el-pager li{
 line-height: 36px;
 height: 36px;
 font-size: 20px;
 }
 .el-pagination__total{
 color: #333333;
 }
 button,span:not([class*=suffix]){
 height: 36px;
 line-height: 36px;
 font-size: 20px;
 color: #333333;
 }
 .el-pagination__editor.el-input{
 font-size: 20px;
 }
 }
</style>
