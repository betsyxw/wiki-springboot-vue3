<template>
    <a-layout>
      <a-layout-content
              :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }"
      >
        <div class="about">
          <h1>电子书后台管理</h1>
            <a-table :columns="columns" :row-key="record=> record.id"
                     :data-source="ebooks" :pagination="pagination"
                     :loading="loading" @change="handleTableChange">
                <template #cover="{ text:cover }">
                    <img v-if="cover" :src="cover" alt="avatar">
                </template>
                <template v-slot:action="{text,record}">
                    <a-space size="small">
                        <a-button type="primary">编辑</a-button>
                        <a-button type="danger">删除</a-button>
                    </a-space>
                </template>
            </a-table>
        </div>
      </a-layout-content>
    </a-layout>
</template>


<script lang="ts">
    import axios from "axios";
    import { defineComponent ,onMounted, ref} from 'vue';

    export default defineComponent({
        name:'AdminEbook',
        setup() {
            const ebooks = ref();
            const pagination = ref({
                current: 1,
                pageSize: 2,
                total: 0
            });
            const loading = ref(false);
            const columns = [{
                title: '封面',
                dataIndex: 'cover',
                slots:{customRender: 'cover'}
            },
                {
                    title: '名字',
                    dataIndex: 'name',
                },
                {
                    title: '分类一',
                    key: 'category1Id'
                },
                {
                    title: '文档数',
                    key: 'docCount'
                },
                {
                    title: '阅读数',
                    key: 'viewCount'
                },
                {
                    title: '点赞数',
                    key: 'voteCount'
                },
                {
                    title: 'Aciton',
                    key: 'aciton',
                    slots:{customRender: 'action'}
                }
            ];
            /**
             * 数据查询
             * **/
            const handleQuery = (params: any)=>{
                loading.value = true;
                axios.get("/ebook/list",params).then((response)=>{
                    loading.value = false;
                    const data = response.data;
                    ebooks.value = data.content;

                    //重置分页
                    pagination.value.current = params.page;
                });
            };
            /**
             * 表格点击
             * **/
            const handleTableChange = (pagination: any)=>{
                console.log("查看分页参数"+pagination);
                handleQuery({
                   page: pagination.current,
                   size: pagination.pageSize
                });
            };

            onMounted(()=>{
                handleQuery({});
            });
            return{
                ebooks,
                pagination,
                columns,
                loading,
                handleTableChange
            }
        }
    });
</script>