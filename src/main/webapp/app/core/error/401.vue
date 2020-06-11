<template>
  <div class="errPage-container">
    <el-button
      icon="el-icon-arrow-left"
      class="back-btn"
      @click="back"
    >
      Back
    </el-button>
    <el-row>
      <el-col :span="12">
        <h1 class="text-jumbo text-ginormous">
          Oops!
        </h1>
        <h2>No Coffees</h2>
        <h6>You are not allowed to access the page</h6>
        <ul class="list-unstyled">
          <li class="link-type">
            <router-link to="/">
              Back to Home
            </router-link>
          </li>
          <li>
            <a
              href="#"
              @click.prevent="showLogin"
            >Login</a>
          </li>
        </ul>
      </el-col>
      <el-col :span="12">
        <img
          :src="errGif"
          class="some-gif"
          width="313"
          height="428"
          alt="Girl has dropped her ice cream."
        >
      </el-col>
    </el-row>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator'
import errGif from '@/assets/401-images/401.gif'
import { DialogEventBus } from '../event/dialog-event-bus'

@Component({
  name: 'Page401'
})
export default class extends Vue {
  private errGif = errGif + '?' + +new Date()
  private ewizardClap = 'https://wpimg.wallstcn.com/007ef517-bafd-4066-aae4-6883632d9646'

  private back() {
    if (this.$route.query.noGoBack) {
      this.$router.push({ path: '/' })
    } else {
      this.$router.go(-1)
    }
  }

  private showLogin() {
    DialogEventBus.$emit('show-login');
  }
}
</script>

<style lang="scss" scoped>
.errPage-container {
  width: 800px;
  max-width: 100%;
  margin: 100px auto;

  .back-btn {
    background: #008489;
    color: #fff;
    border: none!important;
  }

  .some-gif {
    margin: 0 auto;
    display: block;
  }

  .some-img {
    display: block;
    margin: 0 auto;
    width: 100%;
  }

  .text-jumbo {
    font-size: 60px;
    font-weight: 700;
    color: #484848;
  }

  .list-unstyled {
    font-size: 14px;

    li {
      padding-bottom: 5px;
    }

    a {
      color: #008489;
      text-decoration: none;

      &:hover {
        text-decoration: underline;
      }
    }
  }
}
</style>
