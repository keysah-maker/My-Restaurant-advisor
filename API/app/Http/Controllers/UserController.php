<?php

namespace App\Http\Controllers;


use App\Http\Resources\UserResource;
use App\Models\User;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\Hash;
use Illuminate\Support\Facades\Response;
use Illuminate\Support\Facades\Validator;

class UserController extends Controller
{

    public function index()
    {
        return UserResource::Collection(User::all());
    }

    public function store(Request $request)
    {
        $validator = Validator::make($request->all(), [
            'login' => 'required|unique:users',
            'password' => 'required',
            'email' => 'required|unique:users',
            'name' => 'required',
            'firstname' => 'required',
            'age' => 'required'
        ]);

        if ($validator->fails()) {
            return Response::json('Bad Request', 400);
        }

        $password = Hash::make($request->get('password'));

        $var = new User([
            'login' => $request->get('login'),
            'password' => $password,
            'email' => $request->get('email'),
            'name' => $request->get('name'),
            'firstname' => $request->get('firstname'),
            'age' => $request->get('age')
        ]);

        $var->save();

        return Response::json('Created', 201);
    }

    public function login(Request $request)
    {

        $validator = Validator::make($request->all(), [
            'login' => 'required',
            'password' => 'required'
        ]);

        if ($validator->fails()) {
            return Response::json('Bad Request', 400);
        }

        $user = User::where([['login', $request->get('login')]])->first();

        if (!$user)
            return Response::json("Not Found", 404);

        if (empty($user) || Auth::attempt(['login' => $request->get('login'), 'password' => $request->get('password')])) {
            $user = ([
                'id' => $user->id,
                'login' => $user->login,
                'email' => $user->email,
                'name' => $user->name,
                'firstname' => $user->firstname,
                'age' => $user->age]);
            return $user;
        }

        return Response::json('Bad Request', 400);
    }

    public function getUserById($id)
    {
        $user = User::where([['id', $id]])->first();

        if (!$user)
            return Response::json("Not Found", 404);

        return ([
            'id' => $user->id,
            'login' => $user->login,
            'email' => $user->email,
            'name' => $user->name,
            'firstname' => $user->firstname,
            'age' => $user->age]);
    }
}
