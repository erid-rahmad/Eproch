<template>
    <div class="app-container card-view bidding-announcement">
        <div class="toolbar">
            <el-button
                size="mini"
                type="primary"
                @click="saveAsDraft"
            >
                Save as Draft
            </el-button>
            <el-button
                size="mini"
                type="primary"
                @click="openRecipientList"
            >
                Publish
            </el-button>
        </div>
        <div class="card">
            <announcement-form ref="announcementForm"></announcement-form>   
        </div>
    </div>
</template>

<script lang="ts" src="./bidding-announcement.component.ts">
</script>

<style lang="scss">
.compact .bidding-announcement {
  .el-table--mini {

    th,
    td {
        height: 35px;
    }
  }

  .toolbar {
    padding: 4px 16px 0;

    .el-button + .el-button {
      margin-left: 0;
    }
  }
}

</style>
