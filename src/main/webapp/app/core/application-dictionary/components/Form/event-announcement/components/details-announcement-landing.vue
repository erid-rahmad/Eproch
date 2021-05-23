<template>
    <div class="app-container card-view bidding-announcement">
        <div class="card">
    <DetailAnnouncement ref="DetailsAnnouncement"></DetailAnnouncement>
        </div>
    </div>
</template>
<script lang="ts" src="./details-announcement-landing.component.ts"></script>
<style lang="scss">
.compact .bidding-invitation-response {
  .el-table--mini {
    td,
    th {
      height: 35px;
    }
  }
}

.header {
  text-align: center;
  font-size: 15px;
}
</style>
<style lang="scss" scoped>
.bidding-invitation-response {
  .criteria-section {
    &:not(.criteria-0),
    > .el-col > .sub-criteria-section:not(.sub-0) {
      margin-top: 10px;
    }
  }
}
</style>
