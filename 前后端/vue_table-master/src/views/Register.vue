<template>
  <div class="main">
    <div style="height: 100px;width: 100%">
      <img src="../images/header.png" style="width: 100%">
    </div>
    <div class="header">
      <span class="line"></span>
      <span class="text">注册</span>
      <span class="line"></span>
    </div>
    <div class="register">
      <div class="content">
        <p>
          <Input prefix="ios-book" v-model="username" placeholder="请输入用户名" class="input" style=""
                 @on-blur="validateUser()"/>
          <span class="err">{{ errmsg1 }}</span>
        </p>
        <p>
          <Input prefix="ios-book" v-model="password" type="password" placeholder="请输入密码" class="input"
                 @on-blur="validatePass()"/>
          <span class="err">{{ errmsg2 }}</span>
        </p>
        <p>
          <Input prefix="ios-book" v-model="passagain" type="password" placeholder="请再次确定密码" class="input"
                 @on-blur="validatePass2()"/>
          <span class="err">{{ errmsg3 }}</span>
        </p>
        <checkbox style="font-size: 14px"> 我已阅读并同意相关服务条款和隐私政策</checkbox>
        <p>
          <Button type="primary" class="btn" @click="doRegister()">注册</Button>
        </p>
        <router-link to="/login" style="margin-left: 230px;">已有账号，直接登录></router-link>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'Register',
  data() {
    return {
      username: '',
      password: '',
      passagain: '',
      errmsg1: '',
      errmsg2: '',
      errmsg3: ''
    }
  },
  methods: {
    validateUser() {
      let valid = true
      if (this.username === '') {
        this.errmsg1 = '用户名不能为空'
        valid = false
      }

      // eslint-disable-next-line eqeqeq
      if (valid === true) {
        this.errmsg1 = ''
      }
      return valid
    },

    validatePass() {
      let valid = true
      if (this.password === '') {
        this.errmsg2 = '密码不能为空'
        valid = false
      } else {
        var reg = new RegExp(/^\w{6,16}$/)
        valid = reg.test(this.password)
        if (!valid) {
          this.errmsg2 = '密码需为6-16位字符'
        }
      }
      if (valid) {
        this.errmsg2 = ''
      }
      return valid
    },
    validatePass2() {
      let valid = true
      // eslint-disable-next-line eqeqeq
      if (this.passagain === '') {
        valid = false
        this.errmsg3 = '请输入确认密码'
      } else {
        // eslint-disable-next-line eqeqeq
        if (this.passagain !== this.password) {
          valid = false
          this.errmsg3 = '两次密码不一致'
        }
      }
      if (valid) {
        this.errmsg3 = ''
      }
      return valid
    },
    validate() {
      const validate1 = this.validateUser()
      const validate2 = this.validatePass()
      const validate3 = this.validatePass2()
      return validate1 && validate2 && validate3
    },

    doRegister() {
      if (this.validate()) {
        axios.post('http://' + this.$store.state.address + ':8090/DDbook/user/reg',
          { username: this.username, password: this.password })
          .then((res) => {
            if (res.data) {
              alert('注册成功，将跳转到登录页面')
              this.$router.push('/login')
              console.log(res)
            }
          })
      }
    }

  }

}
</script>

<style scoped>
.main {

}

.header {
  margin-top: 10px;
  width: 100%;
  height: 36px;
  line-height: 36px;
  /*border:1px solid green;*/
  text-align: center;
  font-size: 40px;
}

.text {
  margin: 20px;
  font-family: 新宋体;
  color: black;
  font-weight: bold;
}

.line {
  display: inline-block;
  width: 40%;
  border-top: 1px solid #cccccc;
  vertical-align: 10px;
}

.content {
  width: 100%;
  height: 330px;
  margin-left: 480px;
}

.register {
  width: 60%;
  margin-top: 50px;
  border-radius: 5px;
  text-align: left;
}

.err {
  color: #ed4014;
}

.register .input {
  width: 350px;
}

.register .btn {
  width: 350px;
  margin-right: 20px;
  height: 40px;
  margin-top: -10px
}

.register p, .register h3 {
  margin-bottom: 15px;
  margin-top: 30px;
}
</style>
