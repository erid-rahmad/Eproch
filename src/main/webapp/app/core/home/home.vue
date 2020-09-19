<template>
  <div class="wscn-http-container">
    <div class="wscn-http">
      <div class="pic">
        <img
          class="pic__parent"
          src="@/assets/images/e-Procurement.png"
          alt="404"
        >
        <img
          class="pic__child left"
          src="@/assets/404-images/404-cloud.png"
          alt="404"
        >
        <img
          class="pic__child mid"
          src="@/assets/404-images/404-cloud.png"
          alt="404"
        >
        <img
          class="pic__child right"
          src="@/assets/404-images/404-cloud.png"
          alt="404"
        >
      </div>
      <div class="text">
        <div class="text__oops" v-text="$t('home.subtitle')">
          OpusB e-Procurement
        </div>
        <!--<div class="text__info">
          All rights reserved
          <a
            style="color:#20a0ff"
            href="#"
          >OpusB</a>
        </div>
        <div class="text__headline" v-text="$t('home.subtitle')">
          OpusB e-Procurement
        </div>-->
        <div class="text__info">
           <div 
              class="alert alert-info" 
              v-if="!authenticated"
              >
              <span 
                v-if="!authenticated"
                v-html="$t('global.messages.info.authenticated.userSample')"
                >
                - Administrator (login="admin" and password="admin") <br/>- User (login="user" and password="user").
              </span>
            </div>

            <div 
              class="alert alert-success" 
              v-if="authenticated"
              >
              <span 
                v-if="username" 
                v-text="$t('home.logged.message', { 'username': username})"
              >
                You are logged in as user "{{username}}"
              </span>
            </div>
        </div>
        
        <a 
          href="#"
          v-if="!authenticated" 
          v-on:click="openLogin()" 
          class="text__return-home">
          <i class="el-icon-user-solid"></i> Sign In
        </a>

        <router-link 
            v-if="!authenticated"
            class="text__return-home"
            to="/register">
            <i class="el-icon-s-shop"></i> Register New Account
        </router-link>

      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./home.component.ts">
</script>

<style lang="scss" scoped>
.wscn-http-container {
  transform: translate(-50%,-50%);
  position: absolute;
  top: 40%;
  left: 50%;
}
.wscn-http {
  position: relative;
  width: 1200px;
  padding: 0 50px;
  overflow: hidden;
  .pic {
    position: relative;
    float: left;
    width: 600px;
    overflow: hidden;
    &__parent {
      width: 100%;
    }
    &__child {
      position: absolute;
      &.left {
        width: 80px;
        top: 17px;
        left: 220px;
        opacity: 0;
        animation-name: cloudLeft;
        animation-duration: 2s;
        animation-timing-function: linear;
        animation-fill-mode: forwards;
        animation-delay: 1s;
      }
      &.mid {
        width: 46px;
        top: 10px;
        left: 420px;
        opacity: 0;
        animation-name: cloudMid;
        animation-duration: 2s;
        animation-timing-function: linear;
        animation-fill-mode: forwards;
        animation-delay: 1.2s;
      }
      &.right {
        width: 62px;
        top: 100px;
        left: 500px;
        opacity: 0;
        animation-name: cloudRight;
        animation-duration: 2s;
        animation-timing-function: linear;
        animation-fill-mode: forwards;
        animation-delay: 1s;
      }
      @keyframes cloudLeft {
        0% {
          top: 17px;
          left: 220px;
          opacity: 0;
        }
        20% {
          top: 33px;
          left: 188px;
          opacity: 1;
        }
        80% {
          top: 81px;
          left: 92px;
          opacity: 1;
        }
        100% {
          top: 97px;
          left: 60px;
          opacity: 0;
        }
      }
      @keyframes cloudMid {
        0% {
          top: 10px;
          left: 420px;
          opacity: 0;
        }
        20% {
          top: 40px;
          left: 360px;
          opacity: 1;
        }
        70% {
          top: 130px;
          left: 180px;
          opacity: 1;
        }
        100% {
          top: 160px;
          left: 120px;
          opacity: 0;
        }
      }
      @keyframes cloudRight {
        0% {
          top: 100px;
          left: 500px;
          opacity: 0;
        }
        20% {
          top: 120px;
          left: 460px;
          opacity: 1;
        }
        80% {
          top: 180px;
          left: 340px;
          opacity: 1;
        }
        100% {
          top: 200px;
          left: 300px;
          opacity: 0;
        }
      }
    }
  }
  .text {
    position: relative;
    float: left;
    width: 500px;
    padding: 30px 0;
    overflow: hidden;
    &__oops {
      font-size: 32px;
      font-weight: bold;
      line-height: 40px;
      color: #1482f0;
      opacity: 0;
      margin-bottom: 20px;
      animation-name: slideUp;
      animation-duration: 0.5s;
      animation-fill-mode: forwards;
    }
    &__headline {
      font-size: 20px;
      line-height: 24px;
      color: #222;
      font-weight: bold;
      opacity: 0;
      margin-bottom: 10px;
      animation-name: slideUp;
      animation-duration: 0.5s;
      animation-delay: 0.1s;
      animation-fill-mode: forwards;
    }
    &__info {
      font-size: 13px;
      line-height: 21px;
      color: grey;
      opacity: 0;
      margin-bottom: 30px;
      animation-name: slideUp;
      animation-duration: 0.5s;
      animation-delay: 0.2s;
      animation-fill-mode: forwards;
    }
    //width: 190px;
    //display: block;
    //height: 36px;
    //line-height: 36px;
    &__return-home {
      margin-right: 10px;
      padding: 10px;
      float: left;
      background: #1482f0;
      border-radius: 5px;
      text-align: center;
      color: #ffffff;
      opacity: 0;
      font-size: 14px;
      cursor: pointer;
      animation-name: slideUp;
      animation-duration: 0.5s;
      animation-delay: 0.3s;
      animation-fill-mode: forwards;
    }
    @keyframes slideUp {
      0% {
        transform: translateY(60px);
        opacity: 0;
      }
      100% {
        transform: translateY(0);
        opacity: 1;
      }
    }
  }
}
</style>
