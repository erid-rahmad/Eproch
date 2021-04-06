<template>
  <section class="app-main">
      <keep-alive>
        <router-view :key="key" />
      </keep-alive>
  </section>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator'
import { TagsViewStoreModule as tagsViewStore } from '@/shared/config/store/tags-view-store'

@Component({
  name: 'AppMain'
})
export default class extends Vue {
  get cachedViews() {
    return tagsViewStore.cachedViews
  }

  get key() {
    return this.$route.fullPath
  }
}
</script>

<style lang="scss" scoped>
.app-main {
  /* 50= navbar  50  */
  min-height: calc(100vh - 50px);
  width: 100%;
  position: relative;
  overflow: hidden;
}

.fixed-header+.app-main {
  padding-top: 50px;
  height: 100vh;
  overflow: auto;
}

.hasTagsView {
  .app-main {
    /* 84 = navbar + tags-view = 50 + 34 */
    min-height: calc(100vh - 84px);
  }

  .fixed-header+.app-main {
    padding-top: 84px;
  }
}
</style>
