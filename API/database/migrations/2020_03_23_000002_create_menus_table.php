<?php

use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;
use Illuminate\Support\Facades\Schema;

class CreateMenusTable extends Migration
{
    /**
     * Run the migrations.
     * @table menus
     *
     * @return void
     */
    public function up()
    {
    if (!Schema::hasTable('menus')) {
        Schema::create('menus', function (Blueprint $table) {
            $table->increments('id');
            $table->string('name', 36);
            $table->string('description', 255);
            $table->float('price');
            $table->integer('restaurant_id')->unsigned()->nullable();
            $table->timestamp('created_at')->useCurrent();
            $table->timestamp('updated_at')->useCurrent();
        });
            Schema::table('menus', function(Blueprint $table) {
                $table->foreign('restaurant_id')->references('id')->on('restaurants')->onDelete('cascade');
            });
        }
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
     public function down()
     {
        Schema::drop('menus');
    }
}
