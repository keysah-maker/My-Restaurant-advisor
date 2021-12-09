<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class Restaurant extends Model
{
    protected $table = "restaurants";

    protected $fillable = [
        'name', 'description', 'grade', 'localization', 'phone_number', 'website', 'hours'
    ];

    public function menus()
    {
        return $this->hasMany(Menu::class);
    }

    public function reviews()
    {
        return $this->hasMany(Review::class);
    }
}
