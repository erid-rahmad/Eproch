<template>
  <div>


    <editor v-model="content" />
     <el-button type="primary" size="mini" @click="submit">Sent</el-button>

    <div class="content">
      <h3>Content</h3>
      <pre><code>{{ tes(content) }}</code></pre>
    </div>
  </div>
</template>

<script lang="ts" src="./index.component.ts"></script>



<style lang="scss" scoped>
::v-deep {
  /* Basic editor styles */
  .ProseMirror {
    > * + * {
      margin-top: 0.75em;
    }

    code {
      background-color: rgba(#616161, 0.1);
      color: #616161;
    }
  }

  .content {
    padding: 1rem 0 0;

    h3 {
      margin: 1rem 0 0.5rem;
    }

    pre {
      border-radius: 5px;
      color: #333;
    }

    code {
      display: block;
      white-space: pre-wrap;
      font-size: 0.8rem;
      padding: 0.75rem 1rem;
      background-color:#e9ecef;
      color: #495057;
    }
  }
}
</style>
