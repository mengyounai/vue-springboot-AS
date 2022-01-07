import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import Home2 from '../views/Home2.vue'
import show from '../views/show.vue'
import show2 from '../views/show2.vue'
import login from '../views/Login.vue'
import Register from '../views/Register.vue'
Vue.use(VueRouter)

const routes = [
  {
    path: '/Home',
    name: 'Home',
    component: Home
  },
  {
    path: '/Home2/:id',
    name: 'Home2',
    component: Home2
  },
  {
    path: '/show',
    name: 'show',
    component: show
  },
  {
    path: '/show2',
    name: 'show2',
    component: show2
  },
  {
    path: '/',
    name: 'login',
    component: login
  },
  {
    path: '/Register',
    name: 'Register',
    component: Register
  }
]

const router = new VueRouter({
  routes
})

export default router
