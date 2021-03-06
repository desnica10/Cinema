import Vue from 'vue'
import VueRouter from 'vue-router'

import Router from '../views/Router.vue'
import Login from '../views/Login.vue'
import Register from '../views/Register.vue'

import Profile from '../views/Profile.vue'
import Home from '../views/Home.vue'

import Tickets from '../views/customer/Tickets.vue'
import Watched from '../views/customer/Watched.vue'

import Users from '../views/admin/Users.vue'

import Halls from '../views/manager/Halls.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/register',
    name: 'Register',
    component: Register
  },
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  {
    path: '/',
    name: 'Router',
    component: Router,
    children: [
      {
        path: '/',
        name: 'Home',
        component: Home
      },
      {
        path: '/profile',
        name: 'Profile',
        component: Profile
      },
      {
        path: '/users',
        name: 'Users',
        component: Users
      },
      {
        path: '/halls',
        name: 'Halls',
        component: Halls
      },
      {
        path: '/tickets',
        name: 'Tickets',
        component: Tickets
      },
      {
        path: '/movies',
        name: 'Watched Movies',
        component: Watched
      },
    ],
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
