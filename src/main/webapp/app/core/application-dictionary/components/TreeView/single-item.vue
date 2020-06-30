<template>
  <el-menu-item
    :index="`${model.id}`"
  >
    <svg-icon
      v-if="model.icon"
      :name="model.icon"
    />
    <span>{{ printTitle(model) }}</span>
  </el-menu-item>
</template>
<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';

const SingleItemProps = Vue.extend({
  props: {
    model: {
      type: Object,
      default: () => {
        return null;
      }
    },
    list: {
      type: Array,
      default: () => {
        return [];
      }
    },
    listKey: {
      type: String,
      default: 'items'
    }
  }
});

@Component
export default class SingleItem extends SingleItemProps {
  printTitle({name, translationKey}) {
    const key = `route.${translationKey || name}`;
    return this.$te(key) ? this.$t(key) : name;
  }
}
</script>
