<?php

use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;
use Illuminate\Support\Facades\Schema;

class CreateRestaurantsTable extends Migration
{
    /**
     * Run the migrations.
     * @table restaurants
     *
     * @return void
     */
    public function up()
    {
        if (!Schema::hasTable('restaurants')) {
            Schema::create('restaurants', function (Blueprint $table) {
                $table->increments('id');
                $table->string('name', 64);
                $table->string('description', 255);
                $table->float('grade');
                $table->string('localization', 255);
                $table->string('phone_number', 20);
                $table->string('website', 255);
                $table->string('hours', 255);
                $table->timestamp('created_at')->useCurrent();
                $table->timestamp('updated_at')->useCurrent();
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
        Schema::drop('restaurants');
    }
}
