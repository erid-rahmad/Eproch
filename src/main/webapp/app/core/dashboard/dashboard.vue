<template>
  <div class="dashboard">
    <div
      v-if="dashboards.length > 1"
      class="dashboard-toolbar"
    >
      <el-button-group>
        <el-button
          v-for="dashboard in dashboards"
          :key="dashboard.key"
          type="primary"
          @click="switchDashboard(dashboard.key)"
        >
          {{ dashboard.name }}
        </el-button>
      </el-button-group>
    </div>
    <div
      v-for="item in dashboardItems"
      :key="item.id"
    >
      <watch-list
        v-if="item.paDashboardItem.type === 'WATCHLIST'"
        :name="item.paDashboardItem.adWatchListName"
      />
    </div>
  </div>
</template>
<script lang="ts" src="./dashboard.component.ts"></script>
<style lang="scss">
</style>
