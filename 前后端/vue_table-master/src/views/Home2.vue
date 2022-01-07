<template>
  <div>
<!--    <button @click="click1()">区分用</button>-->
    <div class="top"></div>
    <div class="search">
      <el-form
        ref="form"
        :inline="true"
        :model="formInline"
        class="demo-form-inline"
      >
        <el-form-item label="名称" prop="id">
          <el-input v-model="formInline.id" placeholder="请输入productKey"></el-input>
        </el-form-item>
        <el-form-item label="设备" prop="orderPerson">
          <el-input
            v-model="formInline.orderPerson"
            placeholder="请输入设备名"
          ></el-input>
        </el-form-item>
<!--        <el-form-item label="最晚付款时间" prop="time">-->
<!--          <el-date-picker-->
<!--            v-model="formInline.time"-->
<!--            type="datetimerange"-->
<!--            align="right"-->
<!--            start-placeholder="开始日期"-->
<!--            end-placeholder="结束日期"-->
<!--            :default-time="['12:00:00', '08:00:00']"-->
<!--          >-->
<!--          </el-date-picker>-->
<!--        </el-form-item>-->
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" @click="search"
            >查询</el-button
          >
          <el-button icon="el-icon-edit" @click="resetForm('form')"
            >重置</el-button
          >
        </el-form-item>
      </el-form>
      <div class="addOrDel">
        <el-button
          @click="delCheck(newArr)"
          type="danger"
          icon="el-icon-delete"
          size="small"
          >删除</el-button
        >
        <el-button
          @click="addData"
          type="primary"
          icon="el-icon-star-off"
          size="small"
          >添加设备</el-button
        >
      </div>
      <el-table
        ref="multipleTable"
        :data="productForm"
        border
        style="width: 100%"
        @selection-change="handleSelectionChange"
        :header-cell-style="{textAlign: 'center'}"
        :cell-style="{ textAlign: 'center' }"
      >
        <el-table-column type="selection" width="55"> </el-table-column>
        <el-table-column prop="productKey" label="productKey" width="180"> </el-table-column>
        <el-table-column prop="deviceName" label="设备名称" width="180">
        </el-table-column>
        <el-table-column prop="status" label="状态" width="180">
        </el-table-column>
        <el-table-column prop="createTime" label="创建日期" width="240">
        </el-table-column>
<!--        <el-table-column prop="payMoney" label="缴费金额" width="180">-->
<!--        </el-table-column>-->
        <el-table-column  label="操作">
          <template v-slot="{ row }">
            <el-button
              @click="del(row.productKey)"
              type="danger"
              icon="el-icon-delete"
              size="small"
              >删除</el-button
            >
            <el-button
              type="primary"
              icon="el-icon-edit"
              size="small"
              @click="openBox(row)"
              >编辑</el-button
            >
            <el-button
              type="primary"
              icon="el-icon-edit"
              size="small"
              @click="openData(row)"
            >查看数据</el-button
            >
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-sizes="[3, 5, 10, 20]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
      >
      </el-pagination>
      <!-- 模态框 -->
      <el-dialog
        :title="num === 0 ? '增加信息' : '修改信息'"
        :visible.sync="dialogFormVisible"
      >
        <el-form :model="form">
          <el-form-item label="设备名" :label-width="formLabelWidth">
            <el-input v-model="form.productName" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="id" :label-width="formLabelWidth" style="display: none">
            <el-input v-model="form.ProductKey" autocomplete="off"></el-input>
          </el-form-item>
<!--          <el-form-item label="状态" :label-width="formLabelWidth">-->
<!--            <el-input v-model="form.status" autocomplete="off"></el-input>-->
<!--          </el-form-item>-->
<!--          <el-form-item-->
<!--            v-show="num === 0 ? true : false"-->
<!--            label="缴费日期"-->
<!--            :label-width="formLabelWidth"-->
<!--          >-->
<!--            <el-date-picker-->
<!--              v-model="form.time"-->
<!--              type="datetimerange"-->
<!--              align="right"-->
<!--              start-placeholder="开始日期"-->
<!--              end-placeholder="结束日期"-->
<!--              :default-time="['12:00:00', '08:00:00']"-->
<!--            >-->
<!--            </el-date-picker>-->
<!--          </el-form-item>-->
<!--          <el-form-item label="缴费金额" :label-width="formLabelWidth">-->
<!--            <el-input v-model="form.payMoney" autocomplete="off"></el-input>-->
<!--          </el-form-item>-->
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="saveUpdate(form)">确 定</el-button>
        </div>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
export default {
  async created() {
    this.tableList()
  },
  data() {
    return {
      newArr: [],
      id: [],
      num: 0,
      searchNo: '',
      formInline: {
        id: '',
        orderPerson: '',
        time: ''
      },
      currentPage: 1,
      pageSize: 5,
      total: 0,
      tableData: [
        {
          payMoney: ''
        }
      ],
      newTable: [],
      newData: [],
      items: [],
      dialogFormVisible: false,
      form: {
        ProductKey: '',
        deviceName: '',
        createTime: ''
      },
      formLabelWidth: '120px',
      productForm: {
        ProductKey: '',
        deviceName: '',
        createTime: ''
      },
      productForm2: {
        ProductKey: '',
        deviceName: '',
        createTime: ''
      }
    }
  },
  methods: {
    async tableList() {
      // eslint-disable-next-line no-unused-vars
      var id = this.$route.params.id
      console.log('传入的id:' + id)
      await axios
        .post('http://localhost:8080/api/QueryDevice', {
          productKey: id
        }).then((res) => {
          this.productForm = res.data
          console.log(res.data)
        })
        .catch((error) => {
          console.log(error)
        })
    },
    search() {
      this.orderPerson = this.formInline.orderPerson
      this.searchNo = this.formInline.id
      if (this.searchNo !== '' && this.orderPerson === '') {
        const num = this.tableData.find((item) => {
          return item.id === Number(this.searchNo)
        })
        this.newTable.push(num)
        this.tableData = this.newTable
      } else {
        const order = this.tableData.find((item) => {
          return item.orderPerson === this.orderPerson
        })
        this.newTable.push(order)
        this.tableData = this.newTable
      }
      this.searchNo = ''
      this.orderPerson = ''
    },

    openData(row) {
      this.$router.push('/show2')
    },
    addData() {
      this.dialogFormVisible = true
      this.form = {}
      this.num = 0
      const a = 1233434
      const b = parseFloat(a).toLocaleString()
      console.log(b)
    },
    del(ProductKey) {
      // eslint-disable-next-line no-unused-vars
      var a = confirm('是否退出')
      if (a) {
        axios
          .post('http://localhost:8080/api/deleteproduct', {
            productKey: ProductKey
          }).then((res) => {
            if (res.status === 200) {
              location.reload()
            }
            console.log(res)
          })
          .catch((error) => {
            console.log(error)
          })
        console.log(ProductKey)
      }
    },
    openBox(row) {
      console.log(row)
      this.dialogFormVisible = true
      this.form = row
    },
    saveUpdate(data) {
      console.log(data)
      axios
        .post('http://localhost:8080/api/UpdateProduct', {
          productKey: data.productKey,
          productName: this.form.productName
          // ProductName: this.form.productName
        }).then((res) => {
          if (res.status === 200) {
            // location.reload()
          }
          console.log(res)
        })
        .catch((error) => {
          console.log(error)
        })
    },

    delCheck(data) {
      data.forEach((num) => {
        // console.log(item)
        const i = this.tableData.findIndex((item) => item.id === num)
        this.tableData.splice(i, 1)
      })
    },
    handleSizeChange(val) {
      this.pageSize = this.total / val
      this.tableList()
    },
    handleCurrentChange(val) {
      this.currentPage = val
      this.tableList()
    },
    toggleSelection(rows) {
      if (rows) {
        rows.forEach((row) => {
          this.$refs.multipleTable.toggleRowSelection(row)
        })
      } else {
        this.$refs.multipleTable.clearSelection()
      }
    },
    handleSelectionChange(val) {
      this.multipleSelection = val
      val.forEach((item) => {
        this.newArr.push(item.id)
      })
      // this.tableData.shift(val)
      // console.log(this.newArr)
    },
    resetForm(form) {
      this.$refs[form].resetFields()
    },
    click1() {
      axios.get('http://localhost:8080/api/getData')
        .then((res) => {
          console.log(res)
        })
    }
  }
}
</script>

<style lang="less" scoped>
.top {
  height: 150px;
  background-image: url("../images/topbar.png");
}
.search {
  padding: 20px;
  width: 1250px;
  height: 530px;
  box-shadow: 0 0 5px #ccc;
  margin: 100px auto;
}
/deep/.el-form-item {
  margin-right: 10px;
}
.addOrDel {
  box-sizing: border-box;
  width: 100%;
  height: 50px;
  background-color: #eee;
  padding: 10px;
  margin: 20px 0;
}
.el-pagination {
  margin-top: 20px;
  margin-left: 50%;
  transform: translate(-50%);
}
</style>
